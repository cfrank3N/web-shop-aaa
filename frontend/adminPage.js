window.addEventListener("load", () => {
    adminCheck();
});

async function adminCheck() {

    try {

        const token = sessionStorage.getItem("jwt");
    
        if (token !== null) {

            const response = await fetch("http://localhost:8080/auth/admin", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
            }
            });

            if (!response.ok) {
                window.location.href = "/denied.html";
            }
        
            const message = await response.text();

            document.getElementById("message").textContent = message;

            console.log(message);
        } else {
            window.location.href = "/denied.html"
        }
    } catch (err) {
        console.log("An error occurred when calling the API");
    }
}