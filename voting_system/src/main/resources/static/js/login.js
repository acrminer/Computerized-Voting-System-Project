// Scripts for login page

const form = document.getElementById('loginForm')

form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const response = await fetch('/loginVoter', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ username, password }),
        credentials: "include"
    });

    const message = document.getElementById('message');
    if (response.ok) {
        const text = await response.text();
        if (text === 'Login successful!') {
            message.textContent = text;
            message.style.color = "green";
            setTimeout(() => {
                window.location.href = "/voter/dashboard";
            }, 1000);
        } else {
            message.textContent = "Invalid username or password.";
            message.style.color = "red";
        }
    }
});