// window.addEventListener("load", () => {
//     adminCheck();
// });
window.addEventListener("load", () => adminCheck());
document.addEventListener('DOMContentLoaded', fetchOrders);


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
        const itemsCount = order.productIdAndQty ? Object.keys(order.productIdAndQty).length : 0;
        const customerId = order.appUser?.id ?? order.appUserId ?? '-';
        const created = order.createdAt ? new Date(order.createdAt).toLocaleString() : '-';
        const totalSum = (order.totalSum ?? 0).toFixed(2);

        const row = document.createElement('tr');

        row.innerHTML = `
            <td class="text-center">${order.id}</td>
            <td class="text-center">${customerId}</td>
            <td class="text-center">${created}</td>
            <td class="text-canter">${totalSum}</td>
            <td>${order.items ? order.items.length : 0}</td>
            <td>
                <button class="btn btn-danger btn-sm delete-order-btn" data-order-id="${order.id}">
                    Delete
                </button>
            </td>
        `;

        tableBody.appendChild(row);
    });

    // Add event listeners to update status buttons
    document.querySelectorAll('.update-status-btn').forEach(button => {
        button.addEventListener('click', updateOrderStatus);
    });

    // Add event listeners to delete buttons
    document.querySelectorAll('.delete-order-btn').forEach(button => {
        button.addEventListener('click', deleteOrder);
    });
}

// Update order status
async function deleteOrder(event) {
    const orderId = event.target.getAttribute('data-order-id');
    const token = sessionStorage.getItem("jwt");

    if (!confirm('Are you sure you want to delete this order?')) return;

    try {
        const response = await fetch(`http://localhost:8080/auth/orders/${orderId}`, {
            method: 'DELETE',
            headers: {
                "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) {
            throw new Error('Failed to delete order');
        }

        document.getElementById('message').innerHTML =
            '<div class="alert alert-success">Order deleted successfully!</div>';

        // Refresh the orders list
        fetchOrders();

        // Auto-dismiss the message after 3 seconds
        setTimeout(() => {
            document.getElementById('message').innerHTML = '';
        }, 3000);
    } catch (error) {
        console.error('Error deleting order:', error);
        document.getElementById('message').innerHTML =
            '<div class="alert alert-danger">Failed to delete order. Please try again.</div>';
    }
}

function updateOrderStatus(event) {
    const orderId = event.target.getAttribute('data-order-id');
    const statusSelect = document.querySelector(`.status-select[data-order-id="${orderId}"]`);
    const newStatus = statusSelect.value;

    fetch(`/api/orders/${orderId}/status`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({status: newStatus})
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('message').innerHTML =
                '<div class="alert alert-success">Order status updated successfully!</div>';

            // Auto-dismiss the message after 3 seconds
            setTimeout(() => {
                document.getElementById('message').innerHTML = '';
            }, 3000);
        })
        .catch(error => {
            console.error('Error updating order status:', error);
            document.getElementById('message').innerHTML =
                '<div class="alert alert-danger">Failed to update order status. Please try again.</div>';
        });
}