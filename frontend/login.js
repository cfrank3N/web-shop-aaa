//Adds eventlistener to form to perform login
document.addEventListener("DOMContentLoaded", () => {
  let form = document.getElementById("loginForm");
  if (form != null) {
    form.addEventListener("submit", login);
  }
});

async function login(e) {
  e.preventDefault();

  //Fetch provided credentials from frontend form
  const invalidElement = document.getElementById("wrongCredentials");
  const passWord = document.getElementById("password");
  const userName = document.getElementById("username");

  //Makes a call to the API to check if user is in DB, i.e a registered user, and
  //also checks if the provided username and password matches with a registered user.

  try {
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: userName.value,
        password: passWord.value
      }),
    });

    //If provided username and password doesn't match
    //API sends back a 400 Bad request

    //TODO: add message in form/on page that username or password doesn't match
    if (!response.ok) {
      const errorMessage = await response.text();

      invalidElement.textContent = errorMessage;

      //Check if element has is-invalid already, otherwise add it for user clarity
      if (
        !passWord.classList.contains("is-invalid") &&
        !userName.classList.contains("is-invalid")
      ) {
        passWord.classList.add("is-invalid");
        userName.classList.add("is-invalid");
      }
      console.log(errorMessage);
      return;
    }

    //Simply removes the is-invalid on the form elements to not
    //have it linger until next time they need to use login

      passWord.classList.remove("is-invalid");
      userName.classList.remove("is-invalid");


    //Saves the provided authenticated JWT from the API
    //in sessionStorage for future auth requests to the API
    const jwt = await response.text();
    sessionStorage.setItem("jwt", jwt);

    //Relocates the user to the frontpage after successful login
    window.location.href = "/index.html";
    //TODO: Remove this alert when done with implementation
    alert("Successful login!");
  } catch (err) {
    console.log("Something went wrong");
  }
}
