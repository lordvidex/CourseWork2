function pageInit() {
    setTimeout(hideLoader, 3000)
}

function hideLoader() {
    document.getElementById("loader").style.display = "none"
    document.getElementById("container").style.display = "block"
}