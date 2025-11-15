// === ЛОГИРОВАНИЕ И ИНИЦИАЛИЗАЦИЯ ===
console.log("=== Categories Page Loaded ===");
console.log("Context Path (from window):", window.contextPath);

document.addEventListener("DOMContentLoaded", () => {
    const ctx = window.contextPath || '';




    // === МОДАЛКА ===
    const modal = document.getElementById("modal");
    const modalContent = document.getElementById("modalContent");
    const form = document.getElementById("categoryForm");
    const title = document.getElementById("modalTitle");
    const addBtn = document.getElementById("addBtn");
    const cancelBtn = document.getElementById("cancelBtn");
    const cancelBtn2 = document.getElementById("cancelBtn2");
    const incomeBtn = document.getElementById("incomeBtn");
    const expenseBtn = document.getElementById("expenseBtn");
    const typeInput = document.getElementById("typeInput");
    const iconInput = document.getElementById("iconInput");
    const colorInput = document.getElementById("colorInput");
    const iconGrid = document.getElementById("iconGrid");
    const colorGrid = document.getElementById("colorGrid");
    const formActionInput = document.getElementById("formActionInput");
    const categoryIdInput = document.getElementById("categoryIdInput");

    // Иконки и цвета
    const remixIcons = [
        'ri-wallet-2-fill', 'ri-money-dollar-circle-fill', 'ri-bank-fill', 'ri-coins-fill',
        'ri-bar-chart-2-fill', 'ri-line-chart-fill', 'ri-pie-chart-2-fill',
        'ri-home-4-fill', 'ri-lightbulb-flash-fill', 'ri-water-flash-fill', 'ri-gas-station-fill',
        'ri-fridge-fill', 'ri-plug-fill', 'ri-tools-fill', 'ri-sofa-fill',
        'ri-restaurant-fill', 'ri-cup-fill', 'ri-bowl-fill', 'ri-cake-2-fill',
        'ri-beer-fill', 'ri-goblet-fill',
        'ri-car-fill', 'ri-taxi-fill', 'ri-bus-2-fill', 'ri-truck-fill',
        'ri-bike-fill', 'ri-plane-fill', 'ri-ship-fill', 'ri-gas-station-fill',
        'ri-gamepad-fill', 'ri-movie-fill', 'ri-music-2-fill', 'ri-t-shirt-fill',
        'ri-ball-pen-fill', 'ri-book-2-fill', 'ri-camera-fill', 'ri-gift-fill',
        'ri-heart-fill', 'ri-stethoscope-fill', 'ri-capsule-fill', 'ri-first-aid-kit-fill',
        'ri-hand-heart-fill', 'ri-medicine-bottle-fill', 'ri-hospital-fill', 'ri-run-fill',
        'ri-briefcase-4-fill', 'ri-computer-fill', 'ri-macbook-fill', 'ri-keyboard-box-fill',
        'ri-pen-nib-fill', 'ri-bookmark-3-fill', 'ri-graduation-cap-fill', 'ri-archive-fill',
        'ri-shopping-bag-3-fill', 'ri-shopping-cart-2-fill', 'ri-store-2-fill', 'ri-scales-2-fill',
        'ri-barcode-box-fill', 'ri-t-shirt-2-fill', 'ri-handbag-fill', 'ri-price-tag-3-fill',
        'ri-map-pin-2-fill', 'ri-calendar-fill', 'ri-timer-2-fill', 'ri-flag-fill',
        'ri-sun-fill', 'ri-moon-fill', 'ri-leaf-fill', 'ri-flower-fill'
    ];

    const colors = [
        '#FF5F6D', '#FFC371', '#4CAF50', '#2196F3',
        '#9C27B0', '#FF9800', '#E91E63', '#00BCD4',
        '#607D8B', '#8BC34A', '#F44336', '#3F51B5'
    ];

    // Render icons
    iconGrid.innerHTML = "";
    remixIcons.forEach(icon => {
        const btn = document.createElement("button");
        btn.type = "button";
        btn.className = "p-2 border rounded hover:bg-gray-200 dark:hover:bg-gray-700 flex justify-center items-center text-xl";
        btn.innerHTML = '<i class="' + icon + '"></i>';
        btn.dataset.icon = icon;
        btn.addEventListener("click", () => {
            iconInput.value = icon;
            iconGrid.querySelectorAll("button").forEach(b => b.classList.remove("bg-gray-200", "dark:bg-gray-700"));
            btn.classList.add("bg-gray-200", "dark:bg-gray-700");
        });
        iconGrid.appendChild(btn);
    });

    // Render colors
    colorGrid.innerHTML = "";
    colors.forEach(color => {
        const btn = document.createElement("button");
        btn.type = "button";
        btn.className = "w-6 h-6 rounded-full border-2 border-transparent hover:scale-110 transition";
        btn.style.backgroundColor = color;
        btn.dataset.color = color;
        btn.addEventListener("click", () => {
            colorInput.value = color;
            colorGrid.querySelectorAll("button").forEach(b => b.style.outline = "none");
            btn.style.outline = "3px solid #000";
        });
        colorGrid.appendChild(btn);
    });

    // Type toggle
    const setType = (type) => {
        typeInput.value = type;
        if (type === "income") {
            incomeBtn.className = "flex-1 px-4 py-2 rounded-lg font-medium bg-green-100 dark:bg-green-800/40 text-green-800 dark:text-green-200 border border-green-300 dark:border-green-700 focus:outline-none focus:ring-2 focus:ring-green-400 transition";
            expenseBtn.className = "flex-1 px-4 py-2 rounded-lg font-medium bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 border border-gray-300 dark:border-gray-600 hover:bg-gray-200 dark:hover:bg-gray-700 transition";
        } else {
            expenseBtn.className = "flex-1 px-4 py-2 rounded-lg font-medium bg-red-100 dark:bg-red-800/40 text-red-800 dark:text-red-200 border border-red-300 dark:border-red-700 focus:outline-none focus:ring-2 focus:ring-red-400 transition";
            incomeBtn.className = "flex-1 px-4 py-2 rounded-lg font-medium bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 border border-gray-300 dark:border-gray-600 hover:bg-gray-200 dark:hover:bg-gray-700 transition";
        }
    };

    incomeBtn.addEventListener("click", () => setType("income"));
    expenseBtn.addEventListener("click", () => setType("expense"));

    // Modal open/close
    const openModal = () => {
        modal.classList.remove("hidden");
        modal.setAttribute("aria-hidden", "false");
        document.body.style.overflow = "hidden";
        setTimeout(() => {
            modalContent.classList.remove("scale-95", "opacity-0");
            modalContent.classList.add("scale-100", "opacity-100");
        }, 10);
    };

    const closeModal = () => {
        modalContent.classList.remove("scale-100", "opacity-100");
        modalContent.classList.add("scale-95", "opacity-0");
        setTimeout(() => {
            modal.classList.add("hidden");
            document.body.style.overflow = "";
        }, 200);
    };

    addBtn?.addEventListener("click", () => {
        console.log("Open add modal");
        title.textContent = "Добавить категорию";
        formActionInput.value = "add";
        categoryIdInput.value = "";
        setType("income");
        document.getElementById("categoryName").value = "";
        iconInput.value = "ri-folder-2-fill";
        colorInput.value = "#FF5F6D";
        iconGrid.querySelector(`[data-icon="ri-folder-2-fill"]`)?.click();
        colorGrid.querySelector(`[data-color="#FF5F6D"]`)?.click();
        openModal();
    });

    [cancelBtn, cancelBtn2].forEach(btn => btn?.addEventListener("click", closeModal));
    modal?.addEventListener("click", e => e.target.hasAttribute("data-overlay") && closeModal());

    // Edit buttons
    document.querySelectorAll(".edit-category-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.dataset.id;
            const name = btn.dataset.name;
            const type = btn.dataset.type;
            const color = btn.dataset.color || "#FF5F6D";
            const icon = btn.dataset.icon || "ri-folder-2-fill";

            console.log("Edit category:", { id, name, type, color, icon });

            title.textContent = "Редактировать категорию";
            formActionInput.value = "update";
            categoryIdInput.value = id;
            document.getElementById("categoryName").value = name;
            setType(type);
            iconInput.value = icon;
            colorInput.value = color;

            const iconBtn = iconGrid.querySelector(`[data-icon="${icon}"]`);
            if (iconBtn) {
                iconGrid.querySelectorAll("button").forEach(b => b.classList.remove("bg-gray-200", "dark:bg-gray-700"));
                iconBtn.classList.add("bg-gray-200", "dark:bg-gray-700");
            }

            const colorBtn = colorGrid.querySelector(`[data-color="${color}"]`);
            if (colorBtn) {
                colorGrid.querySelectorAll("button").forEach(b => b.style.outline = "none");
                colorBtn.style.outline = "3px solid #000";
            }

            // Устанавливаем action перед открытием (но окончательно — при submit)
            form.action = ctx + "/categories/update";
            openModal();
        });
    });

    // Form submit — финальная установка action
    form.addEventListener("submit", (e) => {
        const action = formActionInput.value;
        const id = categoryIdInput.value;
        console.log("Form submit:", { action, id });

        if (action === "add") {
            form.action = ctx + "/categories/add";
        } else {
            form.action = ctx + "/categories/update";
        }
    });
});