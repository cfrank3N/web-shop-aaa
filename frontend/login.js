//Adds eventlistener to form to perform login
document.addEventListener("DOMContentLoaded", () => {
  let form = document.getElementById("loginForm");
  if (form != null) {
    form.addEventListener("submit", login);
  }
});

async function login(e) {
  e.preventDefault();

  const usernameFromForm = document.querySelector("#username").value;
  const passwordFromForm = document.querySelector("#password").value;

  try {
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: usernameFromForm,
        password: passwordFromForm,
      }),
    });

    if (!response.ok) {
      const errorMessage = await response.text();
      console.log(errorMessage);
      return;
    }

    const jwt = await response.text();
    localStorage.setItem("jwt", jwt);
    console.log(jwt);

    window.location.href = "/index.html";
  } catch (err) {
    console.log("Something went wrong");
  }
}
