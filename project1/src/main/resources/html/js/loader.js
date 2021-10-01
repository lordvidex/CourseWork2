function pageInit() {
    setTimeout(hideLoader, 2000)
}

function hideLoader() {
    document.getElementById("loader").style.display = "none"
    document.getElementById("container").style.display = "block"
}

// AJAX - functions
function getRandomNumber() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET','/random-number',true);
    xhr.send();

    xhr.onreadystatechange = function (ev) {
        if ((xhr.status === 200) && (xhr.readyState === 4)) {
            alert(xhr.response);
        } else if (xhr.status !== 200) {
            console.log(ev.type, xhr.status, xhr.readyState);
            alert('An error occurred');
        }
    }
}

function sendText() {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/random-number',true);

    const text = document.getElementById('textBox').value;
    xhr.send(text);

    xhr.onreadystatechange = function(ev) {
        if ((xhr.status === 200) && (xhr.readyState === 4)) {
            alert(xhr.response);
        } else if (xhr.status !== 200) {
            console.log(ev.type)
            alert('An error occurred');
        }
    }
}