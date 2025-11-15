

// === ТЕМА (ИСПРАВЛЕНО) ===
const html = document.documentElement;
const savedTheme = localStorage.getItem("theme") || "light";
html.classList.remove("light", "dark");
html.classList.add(savedTheme);
console.log("Theme loaded:", savedTheme);

document.getElementById("themeToggle")?.addEventListener("click", () => {
    const isDark = html.classList.contains("dark");
    const newTheme = isDark ? "light" : "dark";
    html.classList.replace(isDark ? "dark" : "light", newTheme);
    localStorage.setItem("theme", newTheme);
    console.log("Theme toggled to:", newTheme);
});
