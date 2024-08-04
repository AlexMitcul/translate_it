document.getElementById('translationForm').addEventListener('submit', function(event) {
    event.preventDefault();


    const formData = new URLSearchParams({
        text: document.getElementById('text').value,
        sourceLang: document.getElementById('sourceLang').value,
        targetLang: document.getElementById('targetLang').value
    }).toString();

    fetch(`/translate?${formData}`, {
        method: 'POST'
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('result').innerText = data.translatedText;
        })
        .catch(error => {
            if (error.response) {
                document.getElementById('result').innerText = `Ошибка: ${error.response.status} - ${error.response.statusText}`;
            } else if (error.request) {
                document.getElementById('result').innerText = 'Ошибка: Не удалось получить ответ от сервера';
            } else {
                document.getElementById('result').innerText = `Ошибка: ${error.message}`;
            }
        });
});
