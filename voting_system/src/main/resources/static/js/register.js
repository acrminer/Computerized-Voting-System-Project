// Scripts for register page

const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('confirmPassword');
const message = document.getElementById('message');
const form = document.getElementById('registerForm');

// Executed once voter clicks submit button
form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const password = passwordInput.value;
    const confirmPassword = confirmPasswordInput.value;

    if (password !== confirmPassword) {
        message.textContent = "Passwords do not match!";
        message.style.color = "red";
        return;
    }

    const voter = {
        username: document.getElementById('username').value,
        password: password,
        role: document.getElementById('role').value
    };

    const response = await fetch('/addUser', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(voter)
    });
    if (!response.ok){
        message.textContent = "username already exists";
        message.style.color = "red";
        form.reset();
        return;
    }

    message.textContent = "Registration saved successfully!"
    message.style.color = "green";
    setTimeout(() =>{
        message.style.color= "black";
        message.textContent = "Redirecting to login...";
    }, 1000);
    setTimeout(() => {
        window.location.href = "/login";
    }, 2000);

});