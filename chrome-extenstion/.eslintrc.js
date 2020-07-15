module.exports = {
  root: true,
  env: {
    node: true,
    jquery: true
  },
  //extends: ['plugin:vue/recommended', '@vue/prettier', 'eslint:recommended'],
  extends: ['plugin:vue/recommended', '@vue/prettier', 'eslint:recommended'],
  plugins: ['vue'],
  rules: {
    quotes: [
      2,
      'single',
      {
        avoidEscape: true
      }
    ],
    'no-console': 'off',
    'vue/no-v-html': 'off'
  },
  parserOptions: {
    parser: 'babel-eslint'
  }
};
