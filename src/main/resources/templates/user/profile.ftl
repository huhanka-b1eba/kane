<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Kane — Профиль</title>
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
                    <i class="ri-dashboard-fill text-[20px]"></i> <a href="${contextPath}/home">Дашборд</a>
                </li>
                <li class="hover:text-primary cursor-pointer transition flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-exchange-dollar-fill text-[20px]"></i> <a href="${contextPath}/operation">Операции</a>
                </li>
                <li class="hover:text-primary cursor-pointer transition flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-folder-2-fill text-[20px]"></i> <a href="${contextPath}/categories">Категории</a>
                </li>
                <li class="hover:text-primary cursor-pointer transition flex items-center gap-3 text-black dark:text-white">
                    <i class="ri-line-chart-fill text-[20px]"></i> <a href="${contextPath}/analitic">Аналитика</a>
                </li>
            </ul>
        </nav>
    </div>

    <div class="mt-8 border-t border-gray-200 dark:border-gray-700 pt-6 flex flex-col gap-4">
        <button id="themeToggle" class="flex items-center justify-between px-3 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 transition text-black dark:text-white">
            <span>Тема</span> <i class="ri-contrast-2-fill text-[18px]"></i>
        </button>
        <a href="${contextPath}/profile" class="text-primary font-semibold">
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
            Привет, <#if user??>${user.username!"Гость"}<#else>Гость</#if> <span class="fox-emoji">🦊</span>
        </h2>
        <p class="text-[20px] text-gray-600 dark:text-gray-400">
            Настройте личные данные и просмотрите статистику.<br>
            <span class="italic text-gray-500 dark:text-gray-400">"個人のバランスは心のバランスから始まる。"</span>
        </p>
    </section>

    <div class="gap-8 fade-up delay-2">

        <div class="card">
            <div class="flex justify-between items-start mb-6">
                <h3 class="text-[22px] font-semibold text-black dark:text-white">Профиль</h3>
                <button id="editProfileBtn" class="flex items-center gap-1 bg-primary/10 hover:bg-primary/20 text-primary px-3 py-1 rounded-lg text-[14px] transition">
                    <i class="ri-edit-2-fill text-[16px]"></i> Редактировать
                </button>
            </div>

            <div class="space-y-4">
                <div>
                    <p class="text-gray-500 dark:text-gray-400 text-[14px]">Имя</p>
                    <p class="text-black dark:text-white font-medium"><#if user??>${user.username!"—"}<#else>—</#if></p>
                </div>
                <div>
                    <p class="text-gray-500 dark:text-gray-400 text-[14px]">Email</p>
                    <p class="text-black dark:text-white font-medium"><#if user??>${user.email!"—"}<#else>—</#if></p>
                </div>
            </div>

            <div class="mt-8 pt-6 border-t border-gray-200 dark:border-gray-700">
                <a href="${contextPath}/logout" class="flex items-center justify-center gap-2 w-full py-2 bg-gradient-to-r from-red-500 to-red-400 hover:from-red-600 hover:to-red-500 text-white font-medium rounded-lg transition shadow-md hover:shadow-lg">
                    <i class="ri-logout-box-r-line"></i> Выйти из аккаунта
                </a>
            </div>
        </div>


    <footer class="mt-8 text-gray-400 text-[14px] text-center fade-up delay-3">
        <p>© 2025 Kane</p>
        <p class="text-gray-300 dark:text-gray-600 mt-1">禅-трекер</p>
    </footer>
</main>

<div id="profileModal" class="hidden fixed inset-0 z-50 flex items-center justify-center">
    <div class="absolute inset-0 bg-black/40 backdrop-blur-sm" data-overlay></div>
    <div class="bg-white/90 dark:bg-[#1b1b1b]/90 rounded-3xl shadow-xl w-full max-w-md p-6 relative transform transition-all modal-enter border border-gray-200 dark:border-gray-700">
        <button id="closeProfileModal" class="absolute top-4 right-4 text-gray-600 dark:text-gray-300 text-xl hover:text-gray-800 dark:hover:text-white transition">×</button>
        <h3 class="text-[24px] font-semibold mb-4 text-black dark:text-white">Редактировать профиль</h3>

        <form id="profileForm" method="post" action="${contextPath}/profile/update" class="space-y-4">
            <div>
                <label class="block mb-2 text-black dark:text-white font-medium">Имя</label>
                <input type="text" name="username" id="usernameInput" value="<#if user??>${user.username!""}<#else></#if>"
                       class="w-full border border-gray-300 dark:border-gray-600 rounded-lg p-3 bg-white dark:bg-[#111] text-black dark:text-white focus:ring-2 focus:ring-primary focus:border-transparent transition" required />
            </div>

            <div>
                <label class="block mb-2 text-black dark:text-white font-medium">Email</label>
                <input type="email" name="email" id="emailInput" value="<#if user??>${user.email!""}<#else></#if>"
                       class="w-full border border-gray-300 dark:border-gray-600 rounded-lg p-3 bg-white dark:bg-[#111] text-black dark:text-white focus:ring-2 focus:ring-primary focus:border-transparent transition" required />
            </div>

            <div>
                <label class="block mb-2 text-black dark:text-white font-medium">Новый пароль <span class="text-gray-500">(оставьте пустым, чтобы не менять)</span></label>
                <input type="password" name="password"
                       class="w-full border border-gray-300 dark:border-gray-600 rounded-lg p-3 bg-white dark:bg-[#111] text-black dark:text-white focus:ring-2 focus:ring-primary focus:border-transparent transition" />
            </div>

            <div class="flex justify-end gap-3 mt-6">
                <button type="button" id="cancelProfileBtn" class="px-6 py-2 bg-gray-200 dark:bg-gray-700 rounded-lg hover:bg-gray-300 dark:hover:bg-gray-600 transition font-medium">
                    Отмена
                </button>
                <button type="submit" class="px-6 py-2 bg-gradient-to-r from-primary to-secondary text-white rounded-lg hover:from-red-500 hover:to-red-400 transition font-medium">
                    Сохранить
                </button>
            </div>
        </form>
    </div>
</div>

<script src="${contextPath}/static/js/profile.js"></script>
<script src="${contextPath}/static/js/themes/scriptThemeFirst.js"></script>
<script src="${contextPath}/static/js/sakura.js"></script>

</body>
</html>