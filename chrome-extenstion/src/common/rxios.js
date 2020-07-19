import axios from 'axios';
import { from, defer } from 'rxjs';

export default {
  get(url, config) {
    return defer(() => from(axios.get(url, config)));
  },
  post(url, body, config) {
    return defer(() => from(axios.post(url, body, config)));
  },
  put(url, body, config) {
    return defer(() => from(axios.put(url, body, config)));
  },
  delete(url, config) {
    return defer(() => from(axios.delete(url, config)));
  },
};
