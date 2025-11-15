document.addEventListener("DOMContentLoaded", () => {


    /* --- Modal & Form --- */
    const modal = document.getElementById("modal");
    const addBtn = document.getElementById("addBtn");
    const closeBtn = document.getElementById("closeModalBtn");
    const cancelBtn = document.getElementById("cancelBtn");
    const form = document.getElementById("operationForm");
    const title = document.getElementById("modalTitle");
    const typeInput = document.getElementById("typeInput");
    const categorySelect = document.getElementById("categoryInput");
    const actionInput = document.getElementById("actionInput");
    const idInput = document.getElementById("idInput");

    const incomeCategories = JSON.parse(document.getElementById('__incomeCategories__').textContent);
    const expenseCategories = JSON.parse(document.getElementById('__expenseCategories__').textContent);

    function updateCategories() {
        categorySelect.innerHTML = "";
        const cats = typeInput.value === "income" ? incomeCategories : expenseCategories;
        if (!cats || cats.length === 0) {
            const opt = document.createElement("option");
            opt.value = "";
            opt.textContent = "Нет категорий";
            opt.disabled = true;
            categorySelect.appendChild(opt);
            return;
        }
        cats.forEach(c => {
            const opt = document.createElement("option");
            opt.value = c.id;
            opt.textContent = c.name;
            categorySelect.appendChild(opt);
        });
    }

    if (typeInput && categorySelect) {
        updateCategories();
        typeInput.addEventListener("change", updateCategories);
    }

    const openModal = () => {
        if (modal) {
            modal.classList.remove("hidden");
            document.body.style.overflow = "hidden";
        }
    };

    const closeModal = () => {
        if (modal) {
            modal.classList.add("hidden");
            document.body.style.overflow = "";
            if (form) form.reset();
            if (actionInput) actionInput.value = "add";
            if (idInput) idInput.value = "";
            if (title) title.textContent = "Добавить операцию";
            updateCategories();
        }
    };

    if (addBtn) addBtn.addEventListener("click", openModal);
    if (closeBtn) closeBtn.addEventListener("click", closeModal);
    if (cancelBtn) cancelBtn.addEventListener("click", closeModal);

    if (modal) {
        modal.addEventListener("click", (e) => {
            if (e.target.hasAttribute("data-overlay")) closeModal();
        });
    }

    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape" && modal && !modal.classList.contains("hidden")) {
            closeModal();
        }
    });

    /* ✅ Безопасный обработчик редактирования */
    document.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            const id = this.dataset.id;
            const type = this.dataset.type;
            const amount = parseFloat(this.dataset.amount);
            const categoryId = this.dataset.categoryId;
            const note = this.dataset.note || '';

            if (actionInput) actionInput.value = "update";
            if (idInput) idInput.value = id;
            if (typeInput) typeInput.value = type;
            updateCategories();

            requestAnimationFrame(() => {
                const catId = parseInt(categoryId, 10);
                if (categorySelect) {
                    const exists = Array.from(categorySelect.options).some(opt => opt.value == catId);
                    if (exists) {
                        categorySelect.value = catId;
                    } else {
                        // Добавляем текущую категорию, если её нет в списке
                        const opt = document.createElement("option");
                        opt.value = catId;
                        opt.textContent = "⚠️ #" + catId;
                        categorySelect.appendChild(opt);
                        categorySelect.value = catId;
                    }
                }
                if (document.getElementById("amountInput")) {
                    document.getElementById("amountInput").value = isNaN(amount) ? "" : amount.toFixed(2);
                }
                if (document.getElementById("noteInput")) {
                    document.getElementById("noteInput").value = note;
                }
                if (title) title.textContent = "Редактировать операцию";
                openModal();
            });
        });
    });

    if (form) {
        form.addEventListener("submit", (e) => {
            const amountInput = document.getElementById("amountInput");
            const amount = parseFloat(amountInput?.value || "0");
            if (isNaN(amount) || amount <= 0) {
                e.preventDefault();
                alert("Введите сумму больше 0");
                return;
            }
            if (!categorySelect?.value) {
                e.preventDefault();
                alert("Выберите категорию");
                return;
            }
            setTimeout(closeModal, 100);
        });
    }
});