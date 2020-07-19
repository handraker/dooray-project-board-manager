import qs from 'qs';
import axios from 'axios';

axios.defaults.paramsSerializer = (params) => {
  return qs.stringify(params, { arrayFormat: 'comma' });
};
