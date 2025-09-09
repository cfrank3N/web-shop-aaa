// window.addEventListener("load", () => {
//     adminCheck();
// });
window.addEventListener("load", () => adminCheck());
document.addEventListener('DOMContentLoaded', () => {
    const tableBody = document.getElementById('orderTableBody');
    tableBody.addEventListener('click', onOrderTableClick);
    fetchOrders()
});


async function adminCheck() {
    try {
        const token = sessionStorage.getItem("jwt");
        if (!token) return (window.location.href = "/denied.html")

        const res = await fetch("http://localhost:8080/auth/admin", {
            headers: {"Authorization": `Bearer ${token}`}
        });
        if (!res.ok) return (window.location.href = "/denied.html");

        const msg = await res.text();
        document.getElementById("message").textContent = msg;
    } catch (e) {
        console.error(e);
    }
}


// Fetch orders from the backend API
function fetchOrders() {
    const token = sessionStorage.getItem("jwt");
    fetch('http://localhost:8080/auth/orders', {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(orders => {
            displayOrders(orders);
        })
        .catch(error => {
            console.error('Error fetching orders:', error);
            document.getElementById('message').innerHTML =
                '<div class="alert alert-danger">Failed to load orders. Please try again later.</div>';
        });
}

// Display orders in the table
function displayOrders(orders) {
    const tableBody = document.getElementById('orderTableBody');
    tableBody.innerHTML = '';

    if (!Array.isArray(orders) || orders.length === 0) {
        document.getElementById('message').innerHTML =
            '<div class="alert alert-info">No orders found.</div>';
        return;
    }

    orders.forEach(order => {
        const itemsCount = order.productIdAndQty ? Object.values(order.productIdAndQty)
            .reduce((sum, qty) => sum + Number(qty || 0), 0) : 0;
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

function onOrderTableClick(e) {
    const btn = e.target.closest('.delete-order-btn');
    if (!btn) return;

    if (btn.dataset.deleting === '1') return;

    const orderId = btn.getAttribute('data-order-id');
    if (!confirm('Are you sure you want to delete this order?')) return;

    btn.dataset.deleting = '1';
    btn.disabled = true;

    deleteOrder(orderId)
        .then(() => {
            document.getElementById('message').innerHTML =
                '<div class="alert alert-success">Order deleted successfully!</div>';
            fetchOrders();
            setTimeout(() => { document.getElementById('message').innerHTML = ''; }, 3000);

        })
        .catch (err => {
            console.error('Error when deleting order:', err);
            document.getElementById('message').innerHTML =
                '<div class="alert alert-danger">Failed to delete order.</div>';
            btn.dataset.deleting = '';
            btn.disabled = false;
        });
}

// Update order status
async function deleteOrder(orderId) {
    const token = sessionStorage.getItem("jwt");
    const response = await fetch(`http://localhost:8080/auth/orders/${orderId}`, {
        method: 'DELETE',
        headers: { "Authorization": `Bearer ${token}` }
    });

    if (response.status === 404) return;
    if (!response.ok) {
        const body = await response.text().catch(() => '');
        throw new Error(`Failed to delete order: ${response.status} ${response.statusText} ${body}`);
    }
}