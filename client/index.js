document.getElementById("submit").addEventListener("click", authHandler);

let loginCred = document.getElementById("loginCred");
let passCred = document.getElementById("passCred");

if(localStorage.getItem("access_token"))
  window.location.href = "customer.html";

async function authHandler(e) {
  e.preventDefault();

  let loginCredentials = {
    login_id: loginCred.value,
    password: passCred.value,
  };

  console.log(loginCredentials);
  const response = await fetch("http://localhost:8080/api/login", {
  method: "POST",
  headers: {
    "Content-Type": "application/json", // Specify the correct content type here
  },
  body: JSON.stringify(loginCredentials),
});

const data = await response.json();
console.log(data);

  localStorage.setItem("access_token", data.access_token);
  
}
