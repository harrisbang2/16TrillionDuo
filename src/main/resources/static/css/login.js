const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});
//
function signIn(){
    let email= document.getElementById("signInEmail").value;
    let password = document.getElementById("signInPassword").value;
    alert(email + password)
    $.ajax({
        type: "POST",
        url: `/users/login`,
        contentType: "application/json",
        data: JSON.stringify({email: email, password: password})
    })
        .done(function (res, status, xhr) {
            alert("로그인 성공")
            //window.location.href = 'localhost:8080';
        })
        .fail(function (xhr, textStatus, errorThrown) {
            alert("아이디 생성 실패")
            console.log('statusCode: ' + xhr.status);
            //window.location.href = 'localhost:8080?error'
        });
}
function signUp(){
    let username= document.getElementById("signUpName").value;
    let email= document.getElementById("signUpEmail").value;
    let password = document.getElementById("signUpPassword").value;
    let introduce = document.getElementById("signUpIntroduce").value;
    alert(email + password + username)
    $.ajax({
        type: "POST",
        url: `/users`,
        contentType: "application/json",
        data: JSON.stringify({email: email, password: password, username: username, introduce: introduce})
    })
        .done(function (res, status, xhr) {
            alert("아이디 생성 성공")
            window.location.href = 'localhost:8080';
        })
        .fail(function (xhr, textStatus, errorThrown) {
            alert("아이디 생성 실패")
            console.log('statusCode: ' + xhr.status);
            window.location.href = 'localhost:8080?error'
        });
}