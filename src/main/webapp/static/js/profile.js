
// Modal
const modal = document.getElementById("profileModal");
const openBtn = document.getElementById("editProfileBtn");
const closeBtn = document.getElementById("closeProfileModal");
const cancelBtn = document.getElementById("cancelProfileBtn");

const openModal = () => {
    modal.classList.remove("hidden");
    document.body.style.overflow = "hidden";
};

const closeModal = () => {
    modal.classList.add("hidden");
    document.body.style.overflow = "";
};

openBtn?.addEventListener("click", openModal);
closeBtn?.addEventListener("click", closeModal);
cancelBtn?.addEventListener("click", closeModal);
modal?.addEventListener("click", e => {
    if (e.target.hasAttribute("data-overlay")) closeModal();
});
document.addEventListener("keydown", e => {
    if (e.key === "Escape" && !modal.classList.contains("hidden")) closeModal();
});