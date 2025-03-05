document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("error-message"); // ❗ 추가된 요소

    // 기존 오류 메시지 초기화
    errorMessage.innerText = "";
    errorMessage.style.display = "none";

    try {
        const response = await fetch("/auth/v1/signin", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("token", data.data.token);
            window.location.href = "/view/profile";
        } else {
            const errorData = await response.json();
            errorMessage.innerText = errorData.message || "로그인에 실패했습니다.";
            errorMessage.style.display = "block";
        }
    } catch (error) {
        console.error("Login error:", error);
        errorMessage.innerText = "로그인 요청 중 오류가 발생했습니다.";
        errorMessage.style.display = "block";
    }
});