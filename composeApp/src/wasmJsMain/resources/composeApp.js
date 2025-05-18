const token = "0e4e8170625bc10329009aabae104089";

function fetchHeroes(name) {
    const url = `https://superheroapi.com/api.php/${token}/search/${encodeURIComponent(name)}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Error de red al obtener héroes.");
            }
            return response.json();
        })
        .then(data => {
            let result = [];

            if (data.response === "success") {
                result = data.results;
            } else {
                console.warn("No se encontraron héroes o hubo un error:", data.error);
            }

            const jsonString = JSON.stringify({
                response: data.response,
                results: result
            });

            if (globalThis.org?.example?.project?.HeroBridge?.sendHeroesFromJs) {
                globalThis.org.example.project.HeroBridge.sendHeroesFromJs(jsonString);
            } else {
                console.error("No se encontró HeroBridge.sendHeroesFromJs");
            }
        })
        .catch(error => {
            console.error("Error en fetchHeroes:", error);

            if (globalThis.org?.example?.project?.HeroBridge?.sendHeroesFromJs) {
                globalThis.org.example.project.HeroBridge.sendHeroesFromJs("[]");
            }
        });
}

window.fetchHeroes = fetchHeroes;
