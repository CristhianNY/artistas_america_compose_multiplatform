(function(config) {
    console.log("Loading custom Webpack config");  // Verificación
    config.devServer = {
        historyApiFallback: true,
        port: 8080,
        open: true,
        static: [
            "kotlin",
            "../../../../composeApp/build/processedResources/wasmJs/main",
            "/Users/cristhianbonilla/Documents/artistas/Artistas/composeApp"
        ],
        client: {
            overlay: {
                errors: true,
                warnings: false
            }
        }
    };
})(config);
