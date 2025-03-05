document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/auth/v1/signin", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    });

    if (response.ok) {
        const data = await response.json();
        localStorage.setItem("token", data.data.token);
        alert("로그인 성공! 프로필 페이지로 이동합니다.");
        window.location.href = "/view/profile";
    } else {
        alert("로그인 실패!");
    }
});