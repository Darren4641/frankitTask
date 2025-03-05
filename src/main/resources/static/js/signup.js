document.getElementById("signupForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/auth/v1/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    });

    if (response.ok) {
        alert("회원가입 성공! 로그인 페이지로 이동합니다.");
        window.location.href = "/view/login";
    } else {
        alert("회원가입 실패!");
    }
});