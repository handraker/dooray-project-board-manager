module.exports = {
  configureWebpack: {
    output: {
      filename: 'contentScript.js'
    },
    optimization: {
      splitChunks: false
    }
  },
  filenameHashing: false
};
