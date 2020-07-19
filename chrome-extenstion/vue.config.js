module.exports = {
  configureWebpack: {
    output: {
      filename: 'contentScript.js',
    },
    optimization: {
      splitChunks: false,
    },
  },
  filenameHashing: true,
  devServer: {
    disableHostCheck: true,
    proxy: {
      '/dpbm': {
        target: 'http://localhost:10081',
      },
      '/v2': {
        target: 'https://nhnent.dooray.com',
      },
    },
  },
};
