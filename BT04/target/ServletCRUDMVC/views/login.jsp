<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Đăng nhập</title>
<style>
  :root{
    --ink:#101820;
    --ink-soft:#1b2530;
    --paper:#f6f4ee;
    --brass:#c9a35d;
    --brass-bright:#e2bd7c;
    --line:rgba(246,244,238,0.14);
    --text-dim:rgba(246,244,238,0.55);
    --danger:#d97757;
    --font-display: 'Iowan Old Style', 'Palatino Linotype', Georgia, serif;
    --font-body: -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif;
    --font-mono: 'JetBrains Mono', 'SFMono-Regular', Consolas, monospace;
  }

  *{ box-sizing: border-box; }

  html, body{
    height: 100%;
    margin: 0;
  }

  body{
    background: var(--ink);
    background-image:
      radial-gradient(circle at 15% 20%, rgba(201,163,93,0.10), transparent 45%),
      radial-gradient(circle at 85% 80%, rgba(201,163,93,0.06), transparent 40%);
    font-family: var(--font-body);
    color: var(--paper);
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 24px;
  }

  h2{ display:none; } /* replaced by styled header inside card */

  .scene{
    position: relative;
    width: 100%;
    max-width: 380px;
  }

  /* the "keyline" signature: a vertical brass line that grows in on load
     and contracts to a bright pulse when a field is focused, evoking a
     key sliding into a lock. */
  .keyline{
    position: absolute;
    left: -22px;
    top: 8px;
    bottom: 8px;
    width: 2px;
    background: linear-gradient(to bottom, transparent, var(--brass) 15%, var(--brass) 85%, transparent);
    transform-origin: top;
    animation: grow-in 0.9s cubic-bezier(.16,1,.3,1) both;
    opacity: 0.6;
  }

  @keyframes grow-in{
    from{ transform: scaleY(0); opacity: 0; }
    to{ transform: scaleY(1); opacity: 0.6; }
  }

  form{
    margin: 0;
  }

  .container{
    background: linear-gradient(180deg, var(--ink-soft), var(--ink));
    border: 1px solid var(--line);
    border-radius: 4px;
    padding: 44px 38px 34px;
    position: relative;
    box-shadow: 0 30px 60px -20px rgba(0,0,0,0.6);
  }

  .eyebrow{
    font-family: var(--font-mono);
    font-size: 11px;
    letter-spacing: 0.28em;
    text-transform: uppercase;
    color: var(--brass);
    margin: 0 0 6px;
  }

  .headline{
    font-family: var(--font-display);
    font-size: 30px;
    font-weight: 400;
    letter-spacing: 0.01em;
    margin: 0 0 32px;
    color: var(--paper);
  }

  .field{
    margin-bottom: 22px;
  }

  label{
    display: block;
    font-family: var(--font-mono);
    font-size: 11px;
    letter-spacing: 0.14em;
    text-transform: uppercase;
    color: var(--text-dim);
    margin-bottom: 9px;
    font-weight: 500;
  }

  label b{ font-weight: 500; }

  input[type="text"],
  input[type="password"]{
    width: 100%;
    padding: 13px 4px;
    background: transparent;
    border: none;
    border-bottom: 1px solid var(--line);
    color: var(--paper);
    font-family: var(--font-body);
    font-size: 15px;
    outline: none;
    transition: border-color 0.25s ease;
  }

  input[type="text"]::placeholder,
  input[type="password"]::placeholder{
    color: rgba(246,244,238,0.28);
  }

  input[type="text"]:focus,
  input[type="password"]:focus{
    border-bottom-color: var(--brass-bright);
  }

  input[type="text"]:focus ~ .underline,
  input[type="password"]:focus ~ .underline{
    transform: scaleX(1);
  }

  .container:focus-within .keyline{
    opacity: 1;
    box-shadow: 0 0 12px 1px rgba(226,189,124,0.5);
  }

  button[type="submit"]{
    width: 100%;
    margin-top: 8px;
    padding: 14px;
    background: var(--brass);
    color: var(--ink);
    border: none;
    border-radius: 3px;
    font-family: var(--font-body);
    font-size: 14px;
    font-weight: 600;
    letter-spacing: 0.04em;
    text-transform: uppercase;
    cursor: pointer;
    transition: background 0.2s ease, transform 0.15s ease;
  }

  button[type="submit"]:hover{
    background: var(--brass-bright);
  }

  button[type="submit"]:active{
    transform: translateY(1px);
  }

  button[type="submit"]:focus-visible,
  input:focus-visible,
  a:focus-visible{
    outline: 2px solid var(--brass-bright);
    outline-offset: 2px;
  }

  .row{
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 20px;
    font-size: 13px;
    color: var(--text-dim);
  }

  .row label{
    display: flex;
    align-items: center;
    gap: 8px;
    text-transform: none;
    letter-spacing: normal;
    font-family: var(--font-body);
    font-size: 13px;
    color: var(--text-dim);
    cursor: pointer;
    margin: 0;
  }

  input[type="checkbox"]{
    appearance: none;
    width: 15px;
    height: 15px;
    border: 1px solid var(--line);
    border-radius: 3px;
    background: transparent;
    display: inline-grid;
    place-content: center;
    cursor: pointer;
    flex-shrink: 0;
  }

  input[type="checkbox"]::before{
    content: "";
    width: 8px;
    height: 8px;
    transform: scale(0);
    background: var(--brass-bright);
    border-radius: 1px;
    transition: transform 0.15s ease;
  }

  input[type="checkbox"]:checked::before{
    transform: scale(1);
  }

  .psw a{
    color: var(--brass-bright);
    text-decoration: none;
    border-bottom: 1px solid transparent;
    transition: border-color 0.2s ease;
  }

  .psw a:hover{
    border-bottom-color: var(--brass-bright);
  }

  .alert{
    margin: 0 0 24px;
    padding: 12px 14px;
    border: 1px solid rgba(217,119,87,0.4);
    border-left: 3px solid var(--danger);
    background: rgba(217,119,87,0.08);
    border-radius: 3px;
    font-family: var(--font-body);
    font-size: 13px;
    font-weight: 500;
    letter-spacing: 0.01em;
    color: #f3c9bb;
    text-transform: none;
    animation: shake-in 0.4s ease;
  }

  @keyframes shake-in{
    0%{ opacity: 0; transform: translateX(-6px); }
    50%{ transform: translateX(3px); }
    100%{ opacity: 1; transform: translateX(0); }
  }

  @media (max-width: 420px){
    .keyline{ display:none; }
    .container{ padding: 36px 26px 28px; }
    .headline{ font-size: 26px; }
  }
</style>
</head>
<body>

<h2>login Form</h2>

<div class="scene">
  <div class="keyline" aria-hidden="true"></div>
<form action="${pageContext.request.contextPath}/login" method="post">
    <div class="container">
      <h1 class="headline">Đăng nhập</h1>



      <div class="field">
        <label for="uname"><b>Username</b></label>
        <input type="text" id="uname" placeholder="Nhập tên đăng nhập" name="uname"
          required autocomplete="username">
      </div>

      <div class="field">
        <label for="psw"><b>Password</b></label>
        <input type="password" id="psw" placeholder="Nhập mật khẩu" name="psw"
          required autocomplete="current-password">
      </div>
      <c:if test="${alert != null}">
        <h3 class="alert">${alert}</h3>
      </c:if>
      <button type="submit">login</button>

      <div class="row">
        <label><input type="checkbox" name="remember"> Remember me</label>
        <span class="psw">Forgot <a href="#">password?</a></span>
      </div>
    </div>
  </form>
</div>

</body>
</html>