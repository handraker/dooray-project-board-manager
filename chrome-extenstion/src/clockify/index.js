import moment from 'moment';
import eventBus from '@/EventBus';

let prevIssueId = null;
let issueId = null;
let currentTime = null;
let startedAt = null;

function onClickTimer(e) {
  e.target.disabled = true;

  if (e.target.innerHTML === '등록') {
    eventBus.$emit('importIssue', issueId);
  } else if (startedAt !== null) {
    fetch('https://alpha-sqlserver-api.cloud.toast.com/dpbm/issue/stop-timer', {
      method: 'POST',
    }).then(() => {
      startedAt = null;
      prevIssueId = null;
    });
  } else {
    const tags = document.querySelectorAll('.tags-in-view .tag-badge');
    const moduleTag = Array.prototype.slice
      .call(tags)
      .filter((tag) => tag.innerHTML.startsWith('0.모듈:'));
    if (moduleTag.length === 0) {
      return;
    }

    const moduleName = moduleTag[0].innerHTML.substr(6);

    fetch(
      `https://alpha-sqlserver-api.cloud.toast.com/dpbm/issue/${prevIssueId}/start-timer`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          moduleName,
        }),
      }
    ).then(() => {
      startedAt = null;
      prevIssueId = null;
    });
  }
}

function clockifyHandler() {
  currentTime = moment();

  let headerToolbar = document.querySelector(
    '#main-wrapper > section > section > section > project-contents-layout > section > project-post-contents > section > section.primary.divide-right-view > div > div > div.detail-header.flex-none > div.header-left-toolbar.pull-left.ng-scope.layout-align-start-center.layout-row'
  );
  if (headerToolbar === null) {
    return;
  }

  let issueTimerBtn = headerToolbar.querySelector('#issue-timer-btn');
  if (issueTimerBtn === null) {
    issueTimerBtn = document.createElement('button');
    issueTimerBtn.id = 'issue-timer-btn';
    issueTimerBtn.className = 'd-toolbar-white-btn';
    issueTimerBtn.innerHTML = '▶ 시작';
    issueTimerBtn.addEventListener('click', onClickTimer);

    headerToolbar.appendChild(issueTimerBtn);
  }

  if (startedAt !== null) {
    issueTimerBtn.innerHTML = `■ 중지 ${moment
      .utc(moment(currentTime).diff(startedAt))
      .format('HH:mm:ss')}`;
  }

  issueId = window.location.pathname.substr(
    window.location.pathname.lastIndexOf('/') + 1
  );
  if (prevIssueId === null || prevIssueId !== issueId) {
    prevIssueId = issueId;
    fetch(`https://alpha-sqlserver-api.cloud.toast.com/dpbm/issue/${issueId}`)
      .then((response) => response.json())
      .then((data) => {
        if (data === null) {
          issueTimerBtn.disabled = false;
          prevIssueId = null;
          startedAt = null;
          issueTimerBtn.innerHTML = '등록';
          issueTimerBtn.style = '';
        } else if (data.startedAt === null) {
          issueTimerBtn.disabled = false;
          startedAt = null;
          issueTimerBtn.innerHTML = '▶ 시작';
          issueTimerBtn.style = '';
        } else {
          issueTimerBtn.disabled = false;
          startedAt = data.startedAt;
          issueTimerBtn.innerHTML = `■ 중지 ${moment
            .utc(moment(currentTime).diff(startedAt))
            .format('HH:mm:ss')}`;
          issueTimerBtn.style = 'color: red';
        }
      });
  }
}

export default clockifyHandler;
