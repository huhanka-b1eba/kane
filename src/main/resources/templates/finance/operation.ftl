<!DOCTYPE html>
<html lang="ru" class="light">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Kane — Операции</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.3.0/fonts/remixicon.css" rel="stylesheet"/>
    <script src="${contextPath}/static/js/tailwindConfig.js"></script>
    <link rel="stylesheet" href="${contextPath}/static/css/style.css">
</head>
<body class="flex min-h-screen">

<aside class="w-[270px] bg-white/70 dark:bg-[#141414]/70 border-r border-gray-200 dark:border-gray-700 flex flex-col justify-between py-6 px-5 backdrop-blur-md shadow-lg transition-all duration-300">
    <div>
        <h1 class="text-[36px] font-extrabold mb-10 text-transparent bg-clip-text bg-gradient-to-r from-primary to-secondary">Kane</h1>
        <nav>
            <ul class="flex flex-col gap-3 text-[18px]">
                <li class="hover:text-primary flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-dashboard-fill"></i>
                    <a href="home">Дашборд</a>
                </li>
                <li class="bg-gradient-to-r from-primary to-secondary text-white rounded-lg py-2 px-3 font-semibold flex items-center gap-3">
                    <i class="ri-exchange-dollar-fill"></i>
                    <a href="operation" class="flex-1">Операции</a>
                </li>
                <li class="hover:text-primary flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-folder-2-fill"></i>
                    <a href="categories">Категории</a>
                </li>
                <li class="hover:text-primary flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-line-chart-fill"></i>
                    <a href="analitic">Аналитика</a>
                </li>
            </ul>
        </nav>
    </div>

    <div class="mt-8 border-t border-gray-200 dark:border-gray-700 pt-6 flex flex-col gap-4">
        <button id="themeToggle" class="flex items-center justify-between px-3 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 transition text-black dark:text-white">
            <span>Тема</span> <i class="ri-contrast-2-fill text-[18px]"></i>
        </button>
        <a href="profile">
            <div class="flex items-center gap-3 hover:bg-gray-100 dark:hover:bg-gray-800 p-2 rounded-xl transition">
                <div>
                    <p class="font-semibold text-[16px] text-black dark:text-white"><#if user??>${user.username!"Гость"}<#else>Гость</#if></p>
                    <p class="text-[14px] text-gray-500 dark:text-gray-400">Профиль</p>
                </div>
            </div>
        </a>
    </div>
</aside>

<main class="flex-1 p-10 relative overflow-hidden">
    <section class="mb-10 text-center fade-up">
        <h2 class="text-[36px] font-semibold mb-2 text-black dark:text-white">
            Список операций <span class="fox-emoji">🦊</span>
        </h2>
        <p class="text-[20px] text-gray-600 dark:text-gray-400">
            Управляй своими доходами и расходами.<br>
            <span class="italic text-gray-500 dark:text-gray-400">"財布のバランスは頭の中のバランスから始まる。"</span>
        </p>
    </section>

    <div class="mb-8 fade-up">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-[28px] font-semibold text-black dark:text-white">Недавние операции</h3>
            <button id="addBtn" class="bg-gradient-to-r from-primary to-secondary text-white px-4 py-2 rounded-lg hover:from-red-500 hover:to-red-400 transition font-semibold">
                Добавить операцию
            </button>
        </div>

        <div class="grid gap-4">
            <#if operations?has_content>
            <#list operations as op>
            <div class="bg-white/90 dark:bg-[#1b1b1b]/80 shadow-md rounded-2xl p-5 flex justify-between items-center backdrop-blur-md hover:shadow-lg transition">
                <div>
                    <p class="text-[18px] font-semibold text-black dark:text-white">
                        <#if op.type == "income">
                        <i class="ri-wallet-2-fill mr-2 text-green-500"></i>
                        <#else>
                        <i class="ri-wallet-2-fill mr-2 text-red-500"></i>
                    </#if>
                    ${(op.note!'Без описания')?html}
                    </p>
                    <p class="text-gray-500 dark:text-gray-400 text-[14px]">
                        Категория: ${op.categoryName!''} · ${op.created_at?string("dd.MM.yyyy HH:mm")}
                    </p>
                </div>
                <div class="flex items-center gap-4">
                    <p class="font-bold text-[22px] <#if op.type == "income">text-green-500<#else>text-red-500</#if>">
                <#if op.type == "income">+<#else>-</#if>₽ ${op.amount?string(",##0.00")}
            </p>

            <button class="text-blue-500 hover:text-blue-700 transition edit-btn"
                    data-id="${op.id}"
                    data-type="${op.type!''}"
                    data-amount="${op.amount}"
                    data-category-id="${op.categoryId}"
                    data-note="${op.note!''?js_string}">
                <i class="ri-edit-2-fill text-[22px]"></i>
            </button>

            <form action="${contextPath}/operation" method="post" onsubmit="return confirm('Удалить операцию?');" style="display:inline;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="${op.id}">
                <button type="submit" class="text-red-500 hover:text-red-700 transition">
                    <i class="ri-delete-bin-6-fill text-[22px]"></i>
                </button>
            </form>
        </div>
    </div>
</#list>
<#else>
<p class="text-gray-500 italic text-center py-8">Операций пока нет — добавь первую 🌸</p>
</#if>
</div>
</div>

<div id="modal" class="hidden fixed inset-0 flex items-center justify-center z-50">
    <div class="absolute inset-0 bg-black/40 backdrop-blur-sm" data-overlay></div>
    <div id="modalContent" class="bg-white/90 dark:bg-[#1b1b1b]/90 rounded-3xl shadow-xl w-full max-w-md p-6 relative transform transition-all modal-enter border border-gray-200 dark:border-gray-700">
        <button id="closeModalBtn" class="absolute top-4 right-4 text-gray-600 dark:text-gray-300 text-xl hover:text-gray-800 dark:hover:text-white transition">×</button>
        <h3 id="modalTitle" class="text-[24px] font-semibold mb-4 text-black dark:text-white">Добавить операцию</h3>
        <form id="operationForm" method="post" action="${contextPath}/operation" class="space-y-4">
            <input type="hidden" name="action" value="add" id="actionInput">
            <input type="hidden" name="id" id="idInput">

            <div>
                <label class="block mb-2 text-black dark:text-white font-medium">Тип операции</label>
                <select name="type" id="typeInput" class="w-full border border-gray-300 dark:border-gray-600 rounded-lg p-3 bg-white dark:bg-[#111] text-black dark:text-white focus:ring-2 focus:ring-primary focus:border-transparent transition">
                    <option value="income">Доход</option>
                    <option value="expense">Расход</option>
                </select>
            </div>

            <div>
                <label class="block mb-2 text-black dark:text-white font-medium">Сумма (₽)</label>
                <input name="amount" id="amountInput" type="number" step="0.01" min="0.01"
                       class="w-full border border-gray-300 dark:border-gray-600 rounded-lg p-3 bg-white dark:bg-[#111] text-black dark:text-white focus:ring-2 focus:ring-primary focus:border-transparent transition"
                       placeholder="0.00" required/>
            </div>

            <div>
                <label class="block mb-2 text-black dark:text-white font-medium">Категория</label>
                <select name="categoryId" id="categoryInput"
                        class="w-full border border-gray-300 dark:border-gray-600 rounded-lg p-3 bg-white dark:bg-[#111] text-black dark:text-white focus:ring-2 focus:ring-primary focus:border-transparent transition" required>
                </select>
            </div>

            <div>
                <label class="block mb-2 text-black dark:text-white font-medium">Описание</label>
                <textarea name="note" id="noteInput" rows="3"
                          class="w-full border border-gray-300 dark:border-gray-600 rounded-lg p-3 bg-white dark:bg-[#111] text-black dark:text-white focus:ring-2 focus:ring-primary focus:border-transparent transition"
                          placeholder="Необязательное описание..."></textarea>
            </div>

            <div class="flex justify-end gap-3 mt-6">
                <button type="button" id="cancelBtn" class="px-6 py-2 bg-gray-200 dark:bg-gray-700 rounded-lg hover:bg-gray-300 dark:hover:bg-gray-600 transition font-medium">
                    Отмена
                </button>
                <button type="submit" class="px-6 py-2 bg-gradient-to-r from-primary to-secondary text-white rounded-lg hover:from-red-500 hover:to-red-400 transition font-medium">
                    Сохранить
                </button>
            </div>
        </form>
    </div>
</div>

<footer class="absolute bottom-4 left-1/2 -translate-x-1/2 text-gray-400 text-[14px] text-center fade-up">
    <p>© 2025 Kane</p>
    <p class="text-gray-300 dark:text-gray-600 mt-1">禅-трекер</p>
</footer>
</main>

<script type="application/json" id="__incomeCategories__">
    <#if incomeCategories?has_content>
    [
    <#list incomeCategories as cat>
      { "id": ${cat.id?c}, "name": "${cat.name?js_string}" }<#if cat_has_next>,</#if>
</#list>
]
<#else>[]</#if>
</script>

<script type="application/json" id="__expenseCategories__">
    <#if expenseCategories?has_content>
    [
    <#list expenseCategories as cat>
      { "id": ${cat.id?c}, "name": "${cat.name?js_string}" }<#if cat_has_next>,</#if>
</#list>
]
<#else>[]</#if>
</script>

<script src="${contextPath}/static/js/operation.js"></script>
<script src="${contextPath}/static/js/themes/scriptThemeFirst.js"></script>
<script src="${contextPath}/static/js/sakura.js"></script>


</body>
</html>