let button = document.getElementById("customer");
const customerList = document.querySelector("#userTable tbody");

button.addEventListener("click", getCustomerList);

async function getCustomerList() {
  clearTable();

  const bearerToken = localStorage.getItem("access_token");

  const response = await fetch("http://localhost:8080/api/getCustomerList", {
    method: "GET",
  });

  const data = await response.json();
  console.log(data);

  if (data.length > 0) {
    data.forEach((user) => {
      let newRow = document.createElement("tr");
      newRow.setAttribute("data-uuid", `${user.uuid}`);
      newRow.innerHTML = `
              <td class="editable" contenteditable="true" >${user.first_name}</td>
              <td class="editable" contenteditable="true" >${user.last_name}</td>
              <td class="editable" contenteditable="true" >${user.street}</td>
              <td class="editable" contenteditable="true" >${user.city}</td>
              <td class="editable" contenteditable="true" >${user.state}</td>
              <td class="editable" contenteditable="true" >${user.email}</td>
              <td class="editable" contenteditable="true" >${user.phone}</td>
              <td class="action-buttons">
                  <button class="update-button" onclick="updateUser('${user.uuid}')">Update</button>
                  <button class="delete-button" onclick="deleteUser('${user.uuid}')">Delete</button>
              </td>
          `;

      customerList.appendChild(newRow);
    });
  }
}

function clearTable() {
  var table = document.getElementById("userTable");

  var tbody = table.getElementsByTagName("tbody")[0];

  tbody.innerHTML = "";
}
function updateUser(uuid) {
  
  try {
    const table = document.getElementById("userTable");
  const rows = table.getElementsByTagName("tr");

  let obj = {};

  for (let i = 0; i < rows.length; i++) {
    const row = rows[i];
    if (row.getAttribute("data-uuid") === uuid) {
      obj.first_name = row.cells[0].innerText;
      obj.last_name = row.cells[1].innerText;
      obj.address = row.cells[2].innerText;
      obj.city = row.cells[3].innerText;
      obj.state = row.cells[4].innerText;
      obj.email = row.cells[5].innerText;
      obj.phone = row.cells[6].innerText;

      break;
    }
  }

    const bearerToken = localStorage.getItem("access_token");
    console.log(uuid, bearerToken, obj)
    fetch(`http://localhost:8080/api/update?uuid=${uuid}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
      body: JSON.stringify(obj),
    })
      .then((response) => response.status)
      .then((data) => {
        console.log("Success:", data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  } catch (error) {
    console.log(error);
  }
}

document
  .getElementById("details-submit")
  .addEventListener("click", createCustomer);

function createCustomer(e) {
  e.preventDefault();

  try {
    let fName = document.getElementById("firstName").value;
    let lName = document.getElementById("lastName").value;
    let street = document.getElementById("street").value;
    let address = document.getElementById("address").value;
    let city = document.getElementById("city").value;
    let state = document.getElementById("state").value;
    let email = document.getElementById("email").value;
    let phone = document.getElementById("phone").value;

    let newCustomer = {
      first_name: fName,
      last_name: lName,
      street: street,
      address: address,
      city: city,
      state: state,
      email: email,
      phone: phone,
    };
    const bearerToken = localStorage.getItem("access_token");
    fetch("http://localhost:8080/api/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
      body: JSON.stringify(newCustomer),
    })
      .then((response) => response.status)
      .then((data) => {
        console.log("Success:", data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  } catch (error) {
    console.log(error);
  }
}

function deleteUser(uuid) {
  const bearerToken = localStorage.getItem("access_token");
  try {
    fetch(`http://localhost:8080/api/delete?uuid=${uuid}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
    })
      .then((response) => response.status)
      .then((data) => {
        console.log("Success:", data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  } catch (error) {
    console.log(error);
  }
}
