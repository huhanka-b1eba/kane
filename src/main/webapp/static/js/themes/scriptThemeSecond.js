// Theme management
const toggle = document.getElementById('themeToggle');
const htmlElement = document.documentElement;

// Check localStorage on page load
const savedTheme = localStorage.getItem('theme');
if (savedTheme) {
    htmlElement.classList.add(savedTheme);
} else {
    // Default to light theme if no preference saved
    htmlElement.classList.add('light');
    localStorage.setItem('theme', 'light');
}

// Toggle theme
toggle.addEventListener('click', () => {
    if (htmlElement.classList.contains('dark')) {
        htmlElement.classList.replace('dark', 'light');
        localStorage.setItem('theme', 'light');
    } else {
        htmlElement.classList.replace('light', 'dark');
        localStorage.setItem('theme', 'dark');
    }
});