// Check if service workers are supported
const isLocalhost = Boolean(
  window.location.hostname === "localhost" ||
    window.location.hostname === "[::1]" ||
    window.location.href.startsWith("http://127.0.0.1/")
);

export function register(config) {
  if ("serviceWorker" in navigator) {
    navigator.serviceWorker
      .register("/service-worker.js")
      .then(() => console.log("Service Worker Registered"))
      .catch((err) => console.error("Service Worker Registration Failed", err));
  }
}
