<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Kane — Вход</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.3.0/fonts/remixicon.css" rel="stylesheet">
    <script src="${contextPath}/static/js/tailwindConfig.js"></script>
    <link rel="stylesheet" href="${contextPath}/static/css/style.css">
</head>
<body class="flex min-h-screen">


<main class="flex-1 flex items-center justify-center p-8 relative overflow-hidden">
    <div class="w-full max-w-md">
        <div class="text-center mb-8 fade-up delay-1">
            <h1 class="text-[36px] font-extrabold mb-4 text-transparent bg-clip-text bg-gradient-to-r from-primary to-secondary">Kane</h1>
            <h2 class="text-[28px] font-semibold mb-2 text-black dark:text-white">С возвращением! <span class="fox-emoji">🦊</span></h2>
            <p class="text-[16px] text-gray-600 dark:text-gray-400">
                Войдите в свой аккаунт<br>
                <span class="italic text-gray-500 dark:text-gray-400">"財布のバランスは頭の中のバランスから始まる。"</span>
            </p>
        </div>

        <div class="auth-card fade-up delay-2">
            <#if error?? && error?has_content>
                <div class="mb-4 p-3 rounded-lg border-l-4 border-red-500 bg-red-50 dark:bg-red-900 text-red-800 dark:text-red-200 animate-fadeIn">
                    <i class="ri-error-warning-line mr-2 align-middle"></i>
                    ${error}
                </div>
            </#if>

            <form class="space-y-4"
                  method="post"
                  action="login">
                <div>
                    <label class="block text-gray-600 dark:text-gray-400 mb-2">Email</label>
                    <input name="email" type="email" class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-[#111] text-gray-900 dark:text-gray-200 placeholder-gray-400" placeholder="your@email.com" required>
                </div>
                <div>
                    <label class="block text-gray-600 dark:text-gray-400 mb-2">Пароль</label>
                    <input name="password" type="password" class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-[#111] text-gray-900 dark:text-gray-200 placeholder-gray-400" placeholder="••••••••" required>
                </div>
                <button type="submit" class="w-full bg-gradient-to-r from-primary to-secondary text-white py-3 rounded-lg hover:from-red-500 hover:to-red-400 transition font-semibold">
                    Войти
                </button>
            </form>

            <div class="mt-6 text-center">
                <p class="text-gray-600 dark:text-gray-400">
                    Ещё нет аккаунта?
                    <a href="register" class="text-primary hover:text-red-500 font-semibold transition">Зарегистрироваться</a>
                </p>
            </div>
        </div>

        <div class="text-center mt-6">
            <button id="themeToggle" class="flex items-center justify-center mx-auto px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 transition text-black dark:text-white">
                <span class="mr-2">Тема</span>
                <i class="ri-contrast-2-fill text-[18px]"></i>
            </button>
        </div>
    </div>

    <footer class="absolute bottom-4 left-1/2 -translate-x-1/2 text-gray-400 text-[14px] text-center fade-up delay-2">
        <p>© 2025 Kane</p>
        <p class="text-gray-300 dark:text-gray-600 mt-1">禅-трекер</p>
    </footer>
</main>

<script src="${contextPath}/static/js/themes/scriptThemeSecond.js"></script>
<script src="${contextPath}/static/js/sakura.js"></script>



</body>
</html>