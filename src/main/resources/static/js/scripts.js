document.getElementById('translationForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new URLSearchParams({
        text: document.getElementById('text').value,
        sourceLang: document.getElementById('sourceLang').value,
        targetLang: document.getElementById('targetLang').value
    }).toString();

    fetch('/translate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    })
        .then(response => response.text().then(text => ({ status: response.status, text })))
        .then(({ status, text }) => {
            if (status === 200) {
                document.getElementById('result').innerText = text;
            } else if (status === 400) {
                document.getElementById('result').innerText = `Ошибка: ${text}`;
            } else {
                document.getElementById('result').innerText = `Ошибка: ${status} ${text}`;
            }
        })
        .catch(error => {
            document.getElementById('result').innerText = `Ошибка: ${error.message}`;
        });
});
