/** @var {object} gsap is a library for building high-performance animations */

if (typeof gsap === 'object') {
    gsap.set("svg", {visibility: "visible"});
    gsap.to("#headStripe", {y: 0.5, rotation: 1, yoyo: true, repeat: -1, ease: "sine.inOut", duration: 1});
    gsap.to("#spaceman", {y: 0.5, rotation: 1, yoyo: true, repeat: -1, ease: "sine.inOut", duration: 1});
    gsap.to("#craterSmall", {x: -3, yoyo: true, repeat: -1, duration: 1, ease: "sine.inOut"});
    gsap.to("#craterBig", {x: 3, yoyo: true, repeat: -1, duration: 1, ease: "sine.inOut"});
    gsap.to("#planet", {
        rotation: -2,
        yoyo: true,
        repeat: -1,
        duration: 1,
        ease: "sine.inOut",
        transformOrigin: "50% 50%"
    });
    gsap.to("#starsBig g", {
        rotation: "random(-30,30)",
        transformOrigin: "50% 50%",
        yoyo: true,
        repeat: -1,
        ease: "sine.inOut"
    });
    gsap.fromTo("#starsSmall g", {scale: 0, transformOrigin: "50% 50%"}, {
        scale: 1,
        transformOrigin: "50% 50%",
        yoyo: true,
        repeat: -1,
        stagger: 0.1
    });
    gsap.to("#circlesSmall circle", {y: -4, yoyo: true, duration: 1, ease: "sine.inOut", repeat: -1});
    gsap.to("#circlesBig circle", {y: -2, yoyo: true, duration: 1, ease: "sine.inOut", repeat: -1});
    gsap.set("#glassShine", {x: -68});
    gsap.to("#glassShine", {
        x: 80,
        duration: 2,
        rotation: -30,
        ease: "expo.inOut",
        transformOrigin: "50% 50%",
        repeat: -1,
        repeatDelay: 8,
        delay: 2
    });
} else {
    console.warn('gsap library is not initialized (network error?)')
}

// {{ if l10n_enabled }}
if (navigator.language.substring(0, 2).toLowerCase() !== 'en') {
    ((s, p) => { // localize the page (details here - https://github.com/tarampampam/error-pages/tree/master/l10n)
        s.src = 'https://cdn.jsdelivr.net/gh/tarampampam/error-pages@2/l10n/l10n.min.js'; // '../l10n/l10n.js';
        s.async = s.defer = true;
        s.addEventListener('load', () => p.removeChild(s));
        p.appendChild(s);
    })(document.createElement('script'), document.body);
}
// {{ end }}