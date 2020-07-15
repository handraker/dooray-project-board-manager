<template>
  <div class="board">
    <ul class="nav nav-tabs">
      <li class="nav-item">
        <a class="nav-link active" href="#">Parent Issue Board</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Configuration</a>
      </li>
    </ul>
    <div>
      <div class="module-board">
        <h4 style="color: rgb(41, 128, 185);">MySQL</h4>
      </div>
      <div>
        <table class="table">
          <colgroup>
            <col style="width: 20px; background-color: rgb(41, 128, 185);" />
            <col />
            <col style="width: 200px;" />
            <col />
            <col style="width: 200px;" />
            <col />
            <col />
            <col style="width: 200px;" />
          </colgroup>
          <thead>
            <tr>
              <th scope="col">
                <input type="checkbox" />
              </th>
              <th scope="col">상위 업무</th>
              <th scope="col">하위 업무</th>
              <th scope="col">개발 상태</th>
              <th scope="col">개발 기한</th>
              <th scope="col">남은 작업일</th>
              <th scope="col">배포 상태</th>
              <th scope="col">배포 기한</th>
              <th scope="col">마일스톤</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th scope="row"><input type="checkbox" /></th>
              <td>[상위업무][MYSQL] 로직 고도화 / UX 개편 (2020.10)</td>
              <td>
                <div class="progress">
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 15%; background-color: #2ecc71; color: black;"
                    aria-valuenow="15"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  >
                    15%
                  </div>
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 30%; background-color: #f1c40f; color: black;"
                    aria-valuenow="30"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  >
                    30%
                  </div>
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 55%; background-color: #ecf0f1; color: black;"
                    aria-valuenow="55"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  >
                    55%
                  </div>
                </div>
              </td>
              <td class="status-cell WORKING">개발 중</td>
              <td>
                <div class="progress">
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 25%; background-color: #2980b9;"
                    aria-valuenow="25"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  ></div>
                  <div class="progress-bar-title">
                    2020년 1월 8일 - 2020년 8월 31일
                  </div>
                </div>
              </td>
              <td>49일</td>
              <td class="status-cell QA_IN_PROGRESS">QA 진행 중</td>
              <td>
                <div class="progress">
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 0%; background-color: #2980b9;"
                    aria-valuenow="0"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  ></div>
                  <div class="progress-bar-title">
                    2020년 9월 1일 - 2020년 10월 13일
                  </div>
                </div>
              </td>
              <td>2020.07.14 (CF.0630)</td>
            </tr>
            <tr>
              <th scope="row"><input type="checkbox" /></th>
              <td>[상위업무][MYSQL] 로직 고도화 / UX 개편 (2020.10)</td>
              <td>
                <div class="progress">
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 15%; background-color: #2ecc71; color: black;"
                    aria-valuenow="15"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  >
                    15%
                  </div>
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 30%; background-color: #f1c40f; color: black;"
                    aria-valuenow="30"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  >
                    30%
                  </div>
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 55%; background-color: #ecf0f1; color: black;"
                    aria-valuenow="55"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  >
                    55%
                  </div>
                </div>
              </td>
              <td class="status-cell WORKING">개발 중</td>
              <td>
                <div class="progress">
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 25%; background-color: #2980b9;"
                    aria-valuenow="25"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  ></div>
                  <div class="progress-bar-title">
                    2020년 1월 8일 - 2020년 8월 31일
                  </div>
                </div>
              </td>
              <td>49일</td>
              <td class="status-cell QA_IN_PROGRESS">QA 진행 중</td>
              <td>
                <div class="progress">
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 0%; background-color: #2980b9;"
                    aria-valuenow="0"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  ></div>
                  <div class="progress-bar-title">
                    2020년 9월 1일 - 2020년 10월 13일
                  </div>
                </div>
              </td>
              <td>2020.07.14 (CF.0630)</td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td style="background-color: white;"></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td>49일</td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {};
  },
  mounted() {
    axios.get('http://localhost:10081/test').then((res) => console.log(res));
  },
  methods: {},
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css">
* {
  font-family: 'Nanum Gothic', Meiryo, 'Noto Sans JP', sans-serif,
    'Lucida Sans Unicode', arial;
}
</style>

<style>
.board {
  width: 100%;
  padding: 0 20px 20px 20px;
}

.module-board > h4 {
  font-weight: bold;
}

.board td.status-cell.WORKING {
  background-color: #f1c40f;
  text-align: center;
  font-weight: bold;
}

.board td.status-cell.QA_IN_PROGRESS {
  color: white;
  background-color: #e74c3c;
  text-align: center;
  font-weight: bold;
}

.board .progress {
  position: relative;
  height: 20px !important;
  background-color: rgb(28, 31, 59) !important;
  margin: 0 !important;
}

.board .progress-bar-title {
  position: absolute;
  text-align: center;
  line-height: 20px; /* line-height should be equal to bar height */
  overflow: hidden;
  color: #fff;
  right: 0;
  left: 0;
  top: 0;
}
</style>
