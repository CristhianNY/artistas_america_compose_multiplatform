const path = require('path');

(function(config) {
    console.log("Loading custom Webpack config");  // Verificación
    config.devServer = {
        historyApiFallback: true,
        port: 8080,
        open: true,
        static: [
            "kotlin",
            path.resolve(__dirname, "../../../../composeApp/build/processedResources/wasmJs/main"),
            path.resolve(__dirname, "../../../../composeApp")
        ],
        client: {
            overlay: {
                errors: true,
                warnings: false
            }
        }
    };
})(config);
