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
    const username = document.getElementById("voterUsername").textContent.trim();

    // Guard: ensure a real candidate is chosen
    if (!candidate) {
        alert("Please select a candidate before voting.");
        button.disabled = false;
        return;
    }

    try {
        const response = await fetch(`/elections/${encodeURIComponent(electionName)}/vote`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ candidate, username })
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

    list.innerHTML = "";
    const template = document.getElementById("electionTemplate");

    data.forEach(election => {
        const clone = template.content.cloneNode(true);
        clone.querySelector(".election-name").textContent = election.electionName;

        const resultsDiv = clone.querySelector(".results");
        Object.entries(election.results).forEach(([name, votes]) => {
            const line = document.createElement("div");
            line.className = "result-line";
            line.innerHTML = `
            <span class="candidate-name">${name}: </span>
            <span class="vote-count">${votes} votes</span>
        `;
            resultsDiv.appendChild(line);
        });

        const votingSection = clone.querySelector(".voting-section");
        const username = document.getElementById("voterUsername").textContent.trim();
        const role = document.getElementById("voterRole").textContent.trim();
        const alreadyVoted = election.voters.includes(username);

        if (role === "voter") {
            if (alreadyVoted) {
                votingSection.innerHTML = "<p>Vote casted. Thank you!</p>";
            } else {
                votingSection.innerHTML = `
                <label for="candidateSelect-${election.electionName}">Choose candidate:</label>
                <select id="candidateSelect-${election.electionName}" class="candidate-select" >
		  <option value="" disabled selected>Select candidate</option>
		  ${Array.from(election.candidates).map(c => `<option value="${c}">${c}</option>`).join("")}
		</select>
                <button class="btn" onclick="vote('${election.electionName}', event)">Vote</button>
            `;
            }
        }

        list.appendChild(clone);
    });
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