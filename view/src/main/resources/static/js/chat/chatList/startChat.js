export function startChat(targetName) {
    return fetch('/messages/startChat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `targetName=${encodeURIComponent(targetName)}`,
    })
        .then(response => response.json());
}