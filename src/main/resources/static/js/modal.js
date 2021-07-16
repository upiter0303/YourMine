const modal1 = document.getElementById('modal1');

// Get the button that opens the modal
const btn1 = document.getElementById("btn-modal1");

// When the user clicks on the button, open the modal
btn1.onclick = function() {
    modal1.style.display = "block";
}

if (document.getElementById('modal2') != null) {

    const modal2 = document.getElementById('modal2');

// Get the button that opens the modal
    const btn2 = document.getElementById("btn-modal2");

// When the user clicks on the button, open the modal
    btn2.onclick = function () {
        modal2.style.display = "block";
    }
}
window.onclick = function(event) {
    if (event.target === modal1) {
        modal1.style.display = "none";
    }
    if (event.target === modal2) {
        modal2.style.display = "none";
    }
}