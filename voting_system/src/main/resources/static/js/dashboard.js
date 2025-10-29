// Scripts for dashboard page

// logout logic
document.getElementById("logoutButton").addEventListener("click", async () => {
    try {
        await fetch('/logout', { method: 'POST' });
        window.location.href = '/login';
    } catch (err) {
        console.error('Logout failed', err);
    }
});

document.addEventListener("DOMContentLoaded", () => {
    loadElections();
});
async function vote(electionName, event) {
    const button = event.target;
    button.disabled = true;

    const select = document.getElementById(`candidateSelect-${electionName}`);
    const candidate = select.value;

    try {
        const response = await fetch(`/elections/${encodeURIComponent(electionName)}/vote`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ candidate })
        });

        if (response.ok) {
            alert(`You voted for ${candidate} in ${electionName}!`);
            loadElections();
        } else {
            const error = await response.text();
            alert("Error submitting vote: " + error);
        }
    } catch (err) {
        alert("Network error: " + err.message);
    } finally {
        button.disabled = false;
    }
}

async function loadElections() {
    const res = await fetch("/elections");
    const data = await res.json();
    const list = document.getElementById("electionList");
    const username = document.getElementById("voterUsername").textContent.trim();
    const role = document.getElementById("voterRole").textContent.trim();

    list.innerHTML = data.map(election => {
        const alreadyVoted = election.voters.includes(username);
        return `
          <div>
            <strong>${election.electionName}</strong><br>
            Candidates: ${Array.from(election.candidates).join(", ")}<br>
            Votes: ${Object.entries(election.results).map(([name, votes]) => `${name}: ${votes}`).join(", ")}<br>
            ${role === 'voter' ? `
                ${alreadyVoted ? '<p>Vote casted. Thank you!</p>' : `
                    <label for="candidateSelect-${election.electionName}">Choose candidate:</label>
                    <select id="candidateSelect-${election.electionName}">
                        ${Array.from(election.candidates).map(c => `<option value="${c}">${c}</option>`).join("")}
                    </select>
                    <button onclick="vote('${election.electionName}', event)">Vote</button>
                `}
            ` : ''}
          </div>
          <hr>
        `;
    }).join("");
}

document.getElementById("addElectionForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const electionName = document.getElementById("electionName").value;
    const candidates = document.getElementById("candidates").value.split(",").map(c => c.trim());

    const response = await fetch("/elections", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ electionName, candidates })
    });

    if (response.ok) {
        alert("Election added successfully!");
        document.getElementById("addElectionForm").reset();
        loadElections();
    } else {
        const error = await response.text();
        alert("Error adding election: " + error);
    }
});