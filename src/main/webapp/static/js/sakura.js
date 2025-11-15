// Sakura
for (let i = 0; i < 20; i++) {
    const p = document.createElement("div");
    p.className = "sakura";
    const s = 8 + Math.random() * 8;
    p.style.width = s + "px";
    p.style.height = s * 0.8 + "px";
    p.style.left = Math.random() * 100 + "vw";
    p.style.animationDuration = (10 + Math.random() * 10) + "s";
    p.style.animationDelay = Math.random() * 5 + "s";
    document.body.appendChild(p);
}