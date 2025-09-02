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
  const usernameFromForm = document.querySelector("#username").value;
  const passwordFromForm = document.querySelector("#password").value;

  //Makes a call to the API to check if user is in DB, i.e a registered user, and
  //also checks if the provided username and password matches with a registered user.
   
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

    //If provided username and password doesn't match 
    //API sends back a 400 Bad request
    
    //TODO: add message in form/on page that username or password doesn't match 
    if (!response.ok) {
      const errorMessage = await response.text();
      console.log(errorMessage);
      return;
    }

    //Saves the provided authenticated JWT from the API 
    //in localStorage for future requests
    const jwt = await response.text();
    localStorage.setItem("jwt", jwt);

    //Relocates the user to the frontpage after successful login
    window.location.href = "/index.html";
    alert("Successful login! Provided JWT: " + jwt);
  } catch (err) {
    console.log("Something went wrong");
  }
}
