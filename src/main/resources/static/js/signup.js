document.getElementById("signupForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("error-message"); // ❗ 오류 메시지 요소

    // 기존 오류 메시지 초기화
    errorMessage.innerText = "";
    errorMessage.style.display = "none";

    try {
        const response = await fetch("/auth/v1/signup", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            alert("회원가입 성공! 로그인 페이지로 이동합니다.");
            window.location.href = "/view/login";
        } else {
            const errorData = await response.json();
            errorMessage.innerText = errorData.message || "회원가입에 실패했습니다.";
            errorMessage.style.display = "block";
        }
    } catch (error) {
        console.error("Signup error:", error);
        errorMessage.innerText = "회원가입 요청 중 오류가 발생했습니다.";
        errorMessage.style.display = "block";
    }
});