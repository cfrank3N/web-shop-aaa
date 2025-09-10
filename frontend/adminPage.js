// Initialize the page when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    const tableBody = document.getElementById('orderTableBody');
    tableBody.addEventListener('click', onOrderTableClick);
    fetchOrders();
});

// Check admin access when page loads
window.addEventListener("load", () => adminCheck());

//Verifies if the current user has admin privileges
async function adminCheck() {
    try {
        const token = sessionStorage.getItem("jwt");
        if (!token) {
            window.location.href = "/denied.html";
            return;
        }

        const res = await fetch("http://localhost:8080/auth/admin", {
            headers: {"Authorization": `Bearer ${token}`}
        });

        if (!res.ok) {
            window.location.href = "/denied.html";
            return;
        }

        const msg = await res.text();
        document.getElementById("message").textContent = msg;
    } catch (e) {
        console.error("Admin check failed:", e);
        window.location.href = "/denied.html";
    }
}

//Fetches all orders from the backend API
function fetchOrders() {
    const token = sessionStorage.getItem("jwt");

    fetch('http://localhost:8080/auth/orders', {
        headers: { "Authorization": `Bearer ${token}` }
    })
    .then(response => {
        if (!response.ok) throw new Error(`Server responded with ${response.status}: ${response.statusText}`);
        return response.json();
    })
    .then(orders => displayOrders(orders))
    .catch(error => {
        console.error('Error fetching orders:', error);
        document.getElementById('message').innerHTML =
            '<div class="alert alert-danger">Failed to load orders. Please try again later.</div>';
    });
}

//Displays orders in the table
function displayOrders(orders,preserveMessage) {
    const tableBody = document.getElementById('orderTableBody');
    tableBody.innerHTML = '';

    if (!Array.isArray(orders) || orders.length === 0) {
        if (!preserveMessage){
            document.getElementById('message').innerHTML =
                '<div class="alert alert-info">No orders found.</div>';
        }
        return;
    }

    orders.forEach(order => {
        const itemsCount = order.productIdAndQty
            ? Object.values(order.productIdAndQty).reduce((sum, qty) => sum + Number(qty || 0), 0)
            : 0;
        const customerId = order.appUser?.id ?? order.customerId ?? '-';
        const created = order.createdAt ? new Date(order.createdAt).toLocaleString() : '-';

        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="text-center">${order.id}</td>
            <td class="text-center">${customerId}</td>
            <td class="text-center">${created}</td>
            <td class="text-center">${itemsCount}</td>
            <td class="text-center">
                <button class="btn btn-danger btn-sm delete-order-btn" data-order-id="${order.id}">
                    Delete
                </button>
            </td>
        `;

        tableBody.appendChild(row);
    });
}

//Shows a message with auto-hide functionality
function showMessage(text, type = "success") {
    const messageElement = document.getElementById('message');
    messageElement.innerHTML = `<div class="alert alert-${type}">${text}</div>`;

    // Auto-hide after 3 seconds
    setTimeout(() => {
        messageElement.innerHTML = '';
    }, 3000);
}

//Handles click events on the order table
function onOrderTableClick(e) {
    const btn = e.target.closest('.delete-order-btn');
    if (!btn || btn.dataset.deleting === '1') return;

    const orderId = btn.getAttribute('data-order-id');
    if (confirm('Are you sure you want to delete this order?')) {
        deleteOrder(orderId, btn);
    }
}


async function deleteOrder(orderId, button) {
    try {
        button.dataset.deleting = '1';
        button.disabled = true;

        const token = sessionStorage.getItem("jwt");
        const response = await fetch(`http://localhost:8080/auth/orders/${orderId}`, {
            method: 'DELETE',
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok && response.status !== 404) {
            throw new Error(`Failed to delete order: ${response.status}`);
        }
        document.getElementById('message').innerHTML =
            '<div class="alert alert-success">Order deleted successfully!</div>';

        // Refresh the orders list
        fetchOrders();

        setTimeout(() => {
            document.getElementById('message').innerHTML = '';
        }, 3000);
    } catch (err) {
        console.error('Error when deleting order:', err);
        document.getElementById('message').innerHTML =
            '<div class="alert alert-danger">Failed to delete order.</div>';
    } finally {
        button.dataset.deleting = '';
        button.disabled = false;
    }
}