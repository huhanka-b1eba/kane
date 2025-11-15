<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${title!"Kane — Дашборд"}</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.3.0/fonts/remixicon.css" rel="stylesheet">
    <script src="${contextPath}/static/js/tailwindConfig.js"></script>
    <link rel="stylesheet" href="${contextPath}/static/css/style.css">
</head>
<body class="flex min-h-screen">

<aside class="w-[270px] bg-white/70 dark:bg-[#141414]/70 border-r border-gray-200 dark:border-gray-700 flex flex-col justify-between py-6 px-5 backdrop-blur-md shadow-lg transition-all duration-300">
    <div>
        <h1 class="text-[36px] font-extrabold mb-10 text-transparent bg-clip-text bg-gradient-to-r from-primary to-secondary">Kane</h1>
        <nav>
            <ul class="flex flex-col gap-3 text-[18px]">
                <li class="bg-gradient-to-r from-primary to-secondary text-white rounded-lg py-2 px-3 font-semibold cursor-pointer flex items-center gap-3">
                    <i class="ri-dashboard-fill text-[20px]"></i>
                    <a href="home">Дашборд</a>
                </li>
                <li class="hover:text-primary cursor-pointer transition flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-exchange-dollar-fill text-[20px]"></i>
                    <a href="operation">Операции</a>
                </li>
                <li class="hover:text-primary cursor-pointer transition flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-folder-2-fill text-[20px]"></i>
                    <a href="categories">Категории</a>
                </li>
                <li class="hover:text-primary cursor-pointer transition flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-line-chart-fill text-[20px]"></i>
                    <a href="analitic">Аналитика</a>
                </li>
            </ul>
        </nav>
    </div>

    <div class="mt-8 border-t border-gray-200 dark:border-gray-700 pt-6 flex flex-col gap-4">
        <button id="themeToggle" class="flex items-center justify-between px-3 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 transition text-black dark:text-white">
            <span>Тема</span>
            <i class="ri-contrast-2-fill text-[18px]"></i>
        </button>
        <a href="profile">
            <div class="flex items-center gap-3 cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-800 transition p-2 rounded-xl">
                <div>
                    <p class="font-semibold text-[16px] text-black dark:text-white"><#if user??>${user.username!"Гость"}<#else>Гость</#if></p>
                    <p class="text-[14px] text-gray-500 dark:text-gray-400">Профиль</p>
                </div>
            </div>
        </a>
    </div>
</aside>

<main class="flex-1 p-10 relative overflow-hidden">

    <section class="mb-10 text-center fade-up delay-1">
        <h2 class="text-[36px] font-semibold mb-2 text-black dark:text-white">
            Добро пожаловать, <#if user??>${user.username!"Гость"}<#else>Гость</#if> <span class="fox-emoji">🦊</span>
        </h2>
        <p class="text-[20px] text-gray-600 dark:text-gray-400">
            Сегодня — хороший день, чтобы упорядочить финансы.<br>
            <span class="italic text-gray-500 dark:text-gray-400">"財布のバランスは頭の中のバランスから始まる。"</span>
        </p>
        <p class="mt-2 text-transparent bg-clip-text bg-gradient-to-r from-pink-400 to-red-400 italic text-lg">🌸 Сакура приветствует тебя! 🌸</p>
    </section>

    <section class="mb-12 fade-up delay-2">
        <h3 class="text-[28px] font-semibold mb-6 text-black dark:text-white">Твоя статистика (統計)</h3>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            <div class="bg-gradient-to-br from-pink-50 to-white dark:from-[#1b1b1b] dark:to-[#141414] rounded-2xl p-6 border border-gray-100 dark:border-gray-700 shadow-sm hover:shadow-md transition backdrop-blur-md">
                <div class="flex items-center gap-3 mb-3">
                    <i class="ri-wallet-3-fill text-[24px] text-primary"></i>
                    <h4 class="text-[18px] text-gray-500 dark:text-gray-400">Баланс</h4>
                </div>
                <p class="text-[32px] font-bold text-primary">
                    ₽ ${(stats.balance)!0?string(",##0.00")}
                </p>
            </div>

            <div class="bg-gradient-to-br from-green-50 to-white dark:from-[#1b1b1b] dark:to-[#141414] rounded-2xl p-6 border border-gray-100 dark:border-gray-700 shadow-sm hover:shadow-md transition backdrop-blur-md">
                <div class="flex items-center gap-3 mb-3">
                    <i class="ri-money-dollar-circle-fill text-[24px] text-green-500"></i>
                    <h4 class="text-[18px] text-gray-500 dark:text-gray-400">Доходы</h4>
                </div>
                <p class="text-[32px] font-bold text-green-500">
                    ₽ ${(stats.income)!0?string(",##0.00")}
                </p>
            </div>

            <div class="bg-gradient-to-br from-red-50 to-white dark:from-[#1b1b1b] dark:to-[#141414] rounded-2xl p-6 border border-gray-100 dark:border-gray-700 shadow-sm hover:shadow-md transition backdrop-blur-md">
                <div class="flex items-center gap-3 mb-3">
                    <i class="ri-hand-coin-fill text-[24px] text-red-500"></i>
                    <h4 class="text-[18px] text-gray-500 dark:text-gray-400">Расходы</h4>
                </div>
                <p class="text-[32px] font-bold text-red-500">
                    ₽ ${(stats.expense)!0?string(",##0.00")}
                </p>
            </div>
        </div>
        </div>
    </section>

    <section class="mb-12 fade-up delay-3">
        <h3 class="text-[28px] font-semibold mb-6 text-black dark:text-white">Последние операции (最近の取引)</h3>

        <#if operations?size == 0>
        <div class="bg-gradient-to-br from-white/90 to-gray-50/70 dark:from-[#1b1b1b]/80 dark:to-[#141414]/80 rounded-2xl shadow-sm p-8 border border-gray-100 dark:border-gray-700 text-center">
            <i class="ri-inbox-line text-[48px] text-gray-400 mb-4"></i>
            <p class="text-[18px] text-gray-600 dark:text-gray-400">Пока нет операций</p>
            <p class="text-gray-500 dark:text-gray-500 mt-2">Добавьте первую операцию во вкладке "Операции"</p>
        </div>
        <#else>
        <div class="space-y-4 pr-2">
            <#list operations as op>
            <#if op.type == "income">
            <#assign bgClass = "bg-green-100 dark:bg-green-900/30">
            <#assign iconClass = "ri-arrow-down-line text-green-600">
            <#assign textClass = "text-green-500">
            <#else>
            <#assign bgClass = "bg-red-100 dark:bg-red-900/30">
            <#assign iconClass = "ri-arrow-up-line text-red-600">
            <#assign textClass = "text-red-500">
        </#if>

        <div class="bg-gradient-to-br from-white/90 to-gray-50/70 dark:from-[#1b1b1b]/80 dark:to-[#141414]/80 rounded-2xl shadow-sm p-6 border border-gray-100 dark:border-gray-700 flex justify-between items-center hover:shadow-md transition backdrop-blur-md">
            <div class="flex items-center gap-4">
                <div class="w-12 h-12 rounded-full flex items-center justify-center ${bgClass}">
                    <i class="${iconClass} text-[20px]"></i>
                </div>
                <div>
                    <p class="text-[18px] font-semibold text-gray-800 dark:text-gray-200">
                        ${op.note!('Без названия')}
                    </p>
                    <p class="text-gray-500 dark:text-gray-400 text-[14px]">
                        ${op.categoryName!('Без категории')} · ${op.created_at?string('dd.MM.yyyy HH:mm')}
                    </p>
                </div>
            </div>
            <div class="text-right">
                <p class="text-[24px] font-bold ${textClass}">
                    <#if op.type == "income">+<#else>-</#if>₽ ${op.amount?string(",##0.00")}
                </p>
                <p class="text-gray-500 dark:text-gray-400 text-[14px] capitalize">
                    <#if op.type == "income">Доход<#else>Расход</#if>
                </p>
            </div>
        </div>
    </#list>
    </div>
</#if>
</section>

<footer class="absolute bottom-4 left-1/2 -translate-x-1/2 text-gray-400 text-[14px] text-center fade-up delay-3">
    <p>© 2025 Kane</p>
    <p class="text-gray-300 dark:text-gray-600 mt-1">禅-трекер</p>
</footer>
</main>

<script src="${contextPath}/static/js/themes/scriptThemeSecond.js"></script>
<script src="${contextPath}/static/js/sakura.js"></script>


</body>
</html>