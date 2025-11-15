<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Kane — В разработке</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.3.0/fonts/remixicon.css" rel="stylesheet">
    <script src="${contextPath}/static/js/tailwindConfig.js"></script>
    <link rel="stylesheet" href="${contextPath}/static/css/style.css">
</head>
<body class="flex min-h-screen">

<!-- MAIN ERROR PAGE -->
<main class="flex-1 flex items-center justify-center p-8 relative overflow-hidden">
    <div class="w-full max-w-2xl text-center">

        <!-- Header -->
        <div class="mb-8 fade-up delay-1">
            <h1 class="text-[36px] font-extrabold mb-4 text-transparent bg-clip-text bg-gradient-to-r from-primary to-secondary">Kane</h1>
        </div>

        <!-- Error Content -->
        <div class="error-card fade-up delay-2">
            <!-- Animated Builder Fox -->
            <div class="builder-fox mb-6">
                <span class="text-6xl">🦊🔧</span>
            </div>

            <!-- Error Number -->
            <div class="error-number mb-4">
                501
            </div>

            <!-- Error Message -->
            <h2 class="text-[28px] font-semibold mb-4 text-black dark:text-white">
                В процессе строительства!
            </h2>

            <p class="text-[18px] text-gray-600 dark:text-gray-400 mb-6 leading-relaxed">
                Наши лисы усердно работают над этой функцией.<br>
                <span class="italic text-gray-500 dark:text-gray-400">"狐たちは懸命に働いています。"</span><br>
                <span class="text-sm">(Лисицы усердно работают)</span>
            </p>

            <!-- Progress Bar -->
            <div class="mb-8">
                <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-3 mb-2">
                    <div class="progress-bar h-3 rounded-full"></div>
                </div>
                <p class="text-sm text-gray-500 dark:text-gray-400">Идёт разработка...</p>
            </div>

            <!-- Action Buttons -->
            <div class="flex flex-col sm:flex-row gap-4 justify-center mb-6">
                <a href="home" class="bg-gradient-to-r from-primary to-secondary text-white px-6 py-3 rounded-lg hover:from-red-500 hover:to-red-400 transition font-semibold flex items-center justify-center gap-2">
                    <i class="ri-home-line"></i>
                    На главную
                </a>
                <button onclick="history.back()" class="bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-200 px-6 py-3 rounded-lg hover:bg-gray-300 dark:hover:bg-gray-600 transition font-semibold flex items-center justify-center gap-2">
                    <i class="ri-arrow-left-line"></i>
                    Назад
                </button>
            </div>

        </div>

        <!-- Theme Toggle -->
        <div class="text-center mt-6 fade-up delay-3">
            <button id="themeToggle" class="flex items-center justify-center mx-auto px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 transition text-black dark:text-white">
                <span class="mr-2">Тема</span>
                <i class="ri-contrast-2-fill text-[18px]"></i>
            </button>
        </div>
    </div>

    <footer class="absolute bottom-4 left-1/2 -translate-x-1/2 text-gray-400 text-[14px] text-center fade-up delay-3">
        <p>© 2025 Kane</p>
        <p class="text-gray-300 dark:text-gray-600 mt-1">禅-трекер</p>
    </footer>
</main>

<script src="${contextPath}/static/js/themes/scriptThemeSecond.js"></script>
<script src="${contextPath}/static/js/sakura.js"></script>

</body>
</html>