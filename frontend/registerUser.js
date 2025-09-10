//Adds eventlistener to form to register user
document.addEventListener("DOMContentLoaded", () => {
  let form = document.getElementById("registerForm");
  if (form != null) {
    form.addEventListener("submit", registerUser);
  }
});

async function registerUser(e) {
  e.preventDefault();

  //get provided user details + error message element if anything goes wrong
  const userName = document.getElementById("username");
  const passWord = document.getElementById("password");
  const roleUser = document.getElementById("user");
  const roleAdmin = document.getElementById("admin");
  const invalidFeedback = document.getElementById("error");

  const roles = [];

  if (roleUser.checked) {
    roles.push("USER");
  }
  if (roleAdmin.checked) {
    roles.push("ADMIN");
  }

  try {
    const response = await fetch("http://localhost:8080/api/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: userName.value,
        password: passWord.value,
        authorities: roles,
      }),
    });

    if (!response.ok) {
      const errorMessage = await response.text();

      invalidFeedback.textContent = errorMessage;
      console.log(errorMessage + "inside the !response.ok");

      if (
        !passWord.classList.contains("is-invalid") &&
        !userName.classList.contains("is-invalid")
      ) {
        passWord.classList.add("is-invalid");
        userName.classList.add("is-invalid");
      }
      return;
    }

    passWord.classList.remove("is-invalid");
    userName.classList.remove("is-invalid");

    const successMessage = await response.text();

    window.location.href = "/login.html";
    alert(successMessage);
  } catch (err) {
    console.log("Something went wrong");
  }
}
