<!DOCTYPE html>
<html lang="ru" class="light">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Kane — Категории</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.3.0/fonts/remixicon.css" rel="stylesheet" />
    <script src="${contextPath}/static/js/tailwindConfig.js"></script>
    <link rel="stylesheet" href="${contextPath}/static/css/style.css">
</head>

<body class="flex min-h-screen" id="body">

<aside class="w-[270px] bg-white/70 dark:bg-[#141414]/70 border-r border-gray-200 dark:border-gray-700 flex flex-col justify-between py-6 px-5 backdrop-blur-md shadow-lg transition-all duration-300">
    <div>
        <h1 class="text-[36px] font-extrabold mb-10 text-transparent bg-clip-text bg-gradient-to-r from-primary to-secondary">Kane</h1>
        <nav>
            <ul class="flex flex-col gap-3 text-[18px]">
                <li class="hover:text-primary flex items-center gap-3"><i class="ri-dashboard-fill"></i><a href="${contextPath}/home">Дашборд</a></li>
                <li class="hover:text-primary flex items-center gap-3"><i class="ri-exchange-dollar-fill"></i><a href="${contextPath}/operation">Операции</a></li>
                <li class="bg-gradient-to-r from-primary to-secondary text-white rounded-lg py-2 px-3 font-semibold flex items-center gap-3"><i class="ri-folder-2-fill"></i><a href="${contextPath}/categories">Категории</a></li>
                <li class="hover:text-primary flex items-center gap-3"><i class="ri-line-chart-fill"></i><a href="${contextPath}/analitic">Аналитика</a></li>
            </ul>
        </nav>
    </div>

    <div class="mt-8 border-t border-gray-200 dark:border-gray-700 pt-6 flex flex-col gap-4">
        <button id="themeToggle" class="flex items-center justify-between px-3 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 transition text-black dark:text-white">
            <span>Тема</span> <i class="ri-contrast-2-fill text-[18px]"></i>
        </button>
        <a href="${contextPath}/profile">
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

    <#if Request??>
    <#assign params = Request.getParameterMap() />
    <#if params.error??>
    <div class="mb-6 p-4 bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-200 rounded-lg border border-red-200 dark:border-red-800">
        <i class="ri-error-warning-line mr-2"></i>
        Нельзя удалить категорию: есть связанные операции.
    </div>
</#if>
</#if>

<div class="flex justify-between items-center mb-8 fade-up delay-1">
    <h2 class="text-[32px] font-semibold text-black dark:text-white">Категории</h2>
    <button id="addBtn" class="bg-primary text-white px-4 py-2 rounded-lg hover:bg-red-500 transition">Добавить категорию</button>
</div>

<div class="grid grid-cols-1 md:grid-cols-2 gap-6 fade-up delay-2">
    <div class="bg-gradient-to-br from-green-50 to-white dark:from-[#1b1b1b] dark:to-[#141414] rounded-2xl shadow-sm p-6 border border-gray-100 dark:border-gray-700 hover:shadow-md transition">
        <h3 class="text-[24px] font-semibold mb-4 text-black dark:text-white">Доходы</h3>
        <ul class="space-y-2">
            <#if incomeCategories?has_content>
            <#list incomeCategories as c>
            <li class="flex justify-between p-2 bg-green-100 dark:bg-green-800/50 rounded-lg text-black dark:text-white">
                <span><i class="${c.icon!}"></i> ${c.name}</span>
                <div class="flex gap-2">
                    <button class="edit-category-btn text-blue-600 hover:text-blue-800 dark:text-blue-400 dark:hover:text-blue-300"
                            data-id="${c.id}"
                            data-name="${c.name?js_string}"
                            data-type="${c.type!}"
                            data-color="${c.color!}"
                            data-icon="${c.icon!}">
                        <i class="ri-edit-2-fill"></i>
                    </button>
                    <form method="post" action="${contextPath}/categories/delete" style="display:inline;" onsubmit="return confirm('Удалить категорию? Это повлияет на все связанные операции!')">
                        <input type="hidden" name="id" value="${c.id}">
                        <button type="submit" class="text-red-600 hover:text-red-800 dark:text-red-400 dark:hover:text-red-300">
                            <i class="ri-delete-bin-6-fill"></i>
                        </button>
                    </form>
                </div>
            </li>
        </#list>
        <#else>
        <li class="text-gray-500 italic">Нет категорий доходов.</li>
    </#if>
    </ul>
</div>

<div class="bg-gradient-to-br from-red-50 to-white dark:from-[#1b1b1b] dark:to-[#141414] rounded-2xl shadow-sm p-6 border border-gray-100 dark:border-gray-700 hover:shadow-md transition">
    <h3 class="text-[24px] font-semibold mb-4 text-black dark:text-white">Расходы</h3>
    <ul class="space-y-2">
        <#if expenseCategories?has_content>
        <#list expenseCategories as c>
        <li class="flex justify-between p-2 bg-red-100 dark:bg-red-800/50 rounded-lg text-black dark:text-white">
            <span><i class="${c.icon!}"></i> ${c.name}</span>
            <div class="flex gap-2">
                <button class="edit-category-btn text-blue-600 hover:text-blue-800 dark:text-blue-400 dark:hover:text-blue-300"
                        data-id="${c.id}"
                        data-name="${c.name?js_string}"
                        data-type="${c.type!}"
                        data-color="${c.color!}"
                        data-icon="${c.icon!}">
                    <i class="ri-edit-2-fill"></i>
                </button>
                <form method="post" action="${contextPath}/categories/delete" style="display:inline;" onsubmit="return confirm('Удалить категорию? Это повлияет на все связанные операции!')">
                    <input type="hidden" name="id" value="${c.id}">
                    <button type="submit" class="text-red-600 hover:text-red-800 dark:text-red-400 dark:hover:text-red-300">
                        <i class="ri-delete-bin-6-fill"></i>
                    </button>
                </form>
            </div>
        </li>
    </#list>
    <#else>
    <li class="text-gray-500 italic">Нет категорий расходов.</li>
</#if>
</ul>
</div>
</div>
</main>

<div id="modal" class="hidden fixed inset-0 z-50 flex items-center justify-center" role="dialog" aria-modal="true">
    <div class="absolute inset-0 bg-black/40 backdrop-blur-sm" data-overlay></div>
    <div id="modalContent" class="bg-white/70 dark:bg-[#1b1b1b]/70 rounded-3xl shadow-xl w-full max-w-md p-6 relative transform transition-all duration-200 ease-out scale-95 opacity-0">
        <button id="cancelBtn" class="absolute top-4 right-4 text-gray-600 dark:text-gray-300 hover:text-gray-800 dark:hover:text-white text-xl" aria-label="Закрыть">&times;</button>
        <h3 id="modalTitle" class="text-[24px] font-semibold mb-4 text-black dark:text-white">Добавить категорию</h3>

        <form id="categoryForm" method="post" class="space-y-4">
            <input type="hidden" name="id" id="categoryIdInput">
            <input type="hidden" name="action" id="formActionInput" value="add">

            <div>
                <label class="block mb-1 text-black dark:text-white">Тип</label>
                <div class="flex gap-3">
                    <input type="hidden" name="type" id="typeInput" value="income" />
                    <button type="button" id="incomeBtn" class="flex-1 px-4 py-2 rounded-lg font-medium bg-green-100 dark:bg-green-800/40 text-green-800 dark:text-green-200 border border-green-300 dark:border-green-700 focus:outline-none focus:ring-2 focus:ring-green-400 transition">Доход</button>
                    <button type="button" id="expenseBtn" class="flex-1 px-4 py-2 rounded-lg font-medium bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 border border-gray-300 dark:border-gray-600 hover:bg-gray-200 dark:hover:bg-gray-700 transition">Расход</button>
                </div>
            </div>

            <div>
                <label for="categoryName" class="block mb-1 text-black dark:text-white">Название категории</label>
                <input id="categoryName" type="text" name="name" class="w-full border border-gray-300 dark:border-gray-600 rounded-lg p-2 bg-white/80 dark:bg-[#111]/80 text-black dark:text-white" placeholder="Например: Еда" required />
            </div>

            <div>
                <label class="block mb-1 text-black dark:text-white">Иконка</label>
                <input type="hidden" name="icon" id="iconInput" value="ri-folder-2-fill">
                <div id="iconGrid" class="grid grid-cols-5 gap-2 max-h-40 overflow-y-auto border border-gray-300 dark:border-gray-600 rounded-lg p-2 bg-white/80 dark:bg-[#111]/80"></div>
            </div>

            <div>
                <label class="block mb-1 text-black dark:text-white">Цвет</label>
                <input type="hidden" name="color" id="colorInput" value="#FF5F6D">
                <div id="colorGrid" class="grid grid-cols-8 gap-2 max-h-20 overflow-y-auto border border-gray-300 dark:border-gray-600 rounded-lg p-2 bg-white/80 dark:bg-[#111]/80"></div>
            </div>

            <div class="flex justify-end gap-3 mt-4">
                <button type="button" id="cancelBtn2" class="px-4 py-2 bg-gray-200 dark:bg-gray-700 rounded-lg hover:bg-gray-300 dark:hover:bg-gray-600 transition text-black dark:text-white">Отмена</button>
                <button type="submit" class="px-4 py-2 bg-primary text-white rounded-lg hover:bg-red-500 transition">Сохранить</button>
            </div>
        </form>
    </div>
</div>

<script src="${contextPath}/static/js/categories.js"></script>
<script src="${contextPath}/static/js/themes/scriptThemeFirst.js"></script>
<script src="${contextPath}/static/js/sakura.js"></script>

</body>
</html>