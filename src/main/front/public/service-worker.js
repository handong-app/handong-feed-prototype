const CACHE_VERSION = "v1";
const CACHE_FILES = "CDNFiles";
const CACHE_WHITE_LIST = [CACHE_VERSION, CACHE_FILES];

self.addEventListener("install", function (event) {
  console.log("Service Worker installing.");
  // Perform install steps
  event.waitUntil(
    caches.open(CACHE_VERSION).then(function (cache) {
      console.log("Opened cache");
      return cache.addAll(["/", "/index.html"]);
    })
  );

  /////// DEBUG
  self.skipWaiting();

  console.log("Service Worker installed.");
});

self.addEventListener("activate", function (event) {
  console.log("Service Worker activating.");
  clearOutdatedCaches();
  clearExpiredCaches(event);

  console.log("Service Worker activated.");
});

// ADD CACHE
self.addEventListener("fetch", (event) => {
  if (event.request.url.includes("https://storage.googleapis.com/")) {
    event.respondWith(
      caches.match(event.request, { ignoreSearch: true }).then((response) => {
        if (response) return response;
        return fetch(event.request).then(async (fetchedResponse) => {
          const cache = await caches.open(CACHE_FILES);
          if (fetchedResponse.type !== "opaque") {
            // Set Cache Timestamp
            const clonedFetchResponse = fetchedResponse.clone();
            const headers = new Headers(clonedFetchResponse.headers);
            headers.append("X-Cache-Timestamp", Date.now()); // 7 days in milliseconds
            const responseWithExpiration = new Response(
              clonedFetchResponse.body,
              {
                status: clonedFetchResponse.status,
                statusText: clonedFetchResponse.statusText,
                headers: headers,
              }
            );
            console.log("NEW CACHE", responseWithExpiration);
            cache.put(event.request, responseWithExpiration.clone());
          }
          return fetchedResponse;
        });
      })
    );
  }
});

const clearExpiredCaches = async (event) => {
  event.waitUntil(
    caches.open(CACHE_FILES).then(async (cache) => {
      const keys = await cache.keys();
      keys.forEach(async (request) => {
        const response = await cache.match(request);
        const expirationDate = response.headers.get("X-Cache-Timestamp");
        if (
          expirationDate &&
          Date.now() - 7 * 24 * 60 * 60 * 1000 > parseInt(expirationDate, 10)
        ) {
          console.log("DELETE CACHE", request.url);
          await cache.delete(request);
        }
      });
    })
  );
};

const clearOutdatedCaches = async () => {
  const cacheNames = await caches.keys();
  console.log("clearOutdatedCaches cacheNames", cacheNames);
  return Promise.all(
    cacheNames
      .filter((cacheName) => !CACHE_WHITE_LIST.includes(cacheName))
      .map((cacheName) => caches.delete(cacheName))
  );
};
