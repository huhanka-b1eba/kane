<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Kane — Аналитика</title>
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
                <li class="hover:text-primary cursor-pointer transition flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-dashboard-fill text-[20px]"></i>
                    <a href="${contextPath}/home">Дашборд</a>
                </li>
                <li class="hover:text-primary cursor-pointer transition flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-exchange-dollar-fill text-[20px]"></i>
                    <a href="${contextPath}/operation">Операции</a>
                </li>
                <li class="hover:text-primary cursor-pointer transition flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-folder-2-fill text-[20px]"></i>
                    <a href="${contextPath}/categories">Категории</a>
                </li>
                <li class="bg-gradient-to-r from-primary to-secondary text-white rounded-lg py-2 px-3 font-semibold cursor-pointer flex items-center gap-3">
                    <i class="ri-line-chart-fill text-[20px]"></i>
                    <a href="${contextPath}/analitic">Аналитика</a>
                </li>
            </ul>
        </nav>
    </div>

    <div class="mt-8 border-t border-gray-200 dark:border-gray-700 pt-6 flex flex-col gap-4">
        <button id="themeToggle" class="flex items-center justify-between px-3 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 transition text-black dark:text-white">
            <span>Тема</span>
            <i class="ri-contrast-2-fill text-[18px]"></i>
        </button>
        <a href="${contextPath}/profile">
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

    <div class="flex justify-between items-center mb-8 fade-up delay-1">
        <h2 class="text-[32px] font-semibold flex items-center gap-2 text-black dark:text-white">
            <i class="ri-line-chart-fill text-[24px]"></i> Аналитика
        </h2>
    </div>

    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-10 fade-up delay-2">
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

        <div class="bg-gradient-to-br from-blue-50 to-white dark:from-[#1b1b1b] dark:to-[#141414] rounded-2xl p-6 border border-gray-100 dark:border-gray-700 shadow-sm hover:shadow-md transition backdrop-blur-md">
            <div class="flex items-center gap-3 mb-3">
                <i class="ri-bar-chart-box-fill text-[24px] text-blue-500"></i>
                <h4 class="text-[18px] text-gray-500 dark:text-gray-400">Операций</h4>
            </div>
            <p class="text-[32px] font-bold text-blue-500">
                ${totalOperations!0}
            </p>
        </div>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 fade-up delay-3">
        <div class="bg-white/80 dark:bg-[#1b1b1b]/80 rounded-2xl shadow p-6 backdrop-blur-md hover:shadow-lg transition">
            <h3 class="text-[22px] font-semibold mb-4 text-black dark:text-white">Расходы по категориям</h3>
            <#if expenseSummary?has_content>
            <#list expenseSummary as item>
            <div class="mb-4">
                <p class="mb-1 flex items-center gap-2 text-black dark:text-white">
                    <i class="ri-coupon-3-fill" style="color: ${item.color!}"></i>
                    ${item.name!} — ₽ ${item.amount?string(",##0.00")!}
                </p>
                <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-4">
                    <div class="h-4 rounded-full" style="background-color: ${item.color!}; width: ${10 + (item.amount / (stats.expense!1) * 90)?int}%"></div>
                </div>
            </div>
        </#list>
        <#else>
        <p class="text-gray-500 italic">Нет расходов</p>
    </#if>
    </div>

    <div class="bg-white/80 dark:bg-[#1b1b1b]/80 rounded-2xl shadow p-6 backdrop-blur-md hover:shadow-lg transition">
        <h3 class="text-[22px] font-semibold mb-4 text-black dark:text-white">Топ расходов</h3>

        <#if expenseSummary?has_content>
        <ul class="space-y-2">
            <#list expenseSummary as item>
            <li class="flex justify-between rounded-lg p-2" style="background-color: ${item.color!}20">
                <span>${item.name!}</span>
                <span>₽ ${item.amount?string(",##0.00")!}</span>
            </li>
        </#list>
        </ul>
        <#else>
        <p class="text-gray-500 text-center py-4">Расходы отсутствуют</p>
    </#if>

    </div>
    </div>

</main>

<script src="${contextPath}/static/js/themes/scriptThemeFirst.js"></script>
<script src="${contextPath}/static/js/sakura.js"></script>

</body>
</html>