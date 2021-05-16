const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require("webpack");

module.exports = (mode) => {
    return {
        mode: mode || "development",
        entry: './src/index.tsx',
        output: {
            filename: 'bundle.js',
            path: path.resolve(__dirname, 'dist'),
        },
        devtool: (mode === "development" || mode === "dev") ? 'source-map' : false,
        devServer: {
            host: 'localhost',
            publicPath: 'http://localhost:3000/dist',
            port: 3000,
            compress: true,
            contentBase: path.join(__dirname, "dist"),
            watchContentBase: true,
            writeToDisk: true,
            proxy: {
                '*': {
                    target: "http://localhost:8080",
                    secure: false
                }
            }
        },
        plugins: [
            new webpack.HotModuleReplacementPlugin(),
            new HtmlWebpackPlugin({
                filename: 'index.html',
                template: 'index.html',
                favicon: 'favicon.png',
                inject: true
            })
        ],
        module: {
            rules: [
                {
                    test: /\.(ts|tsx)$/i,
                    loader: 'ts-loader',
                    exclude: ['/node_modules/'],
                },
                {
                    test: /\.css$/i,
                    use: ['style-loader', 'css-loader'],
                }
            ],
        },
        resolve: {
            extensions: ['.tsx', '.ts', '.js'],
        }
    }
};