import React, { useState } from "react";
import { useNavigate } from "react-router";
import axios from "axios";
import { toast } from "react-hot-toast";

export default function Register() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPass, setShowPass] = useState(false);
  const [loading, setLoading] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      await axios.post("http://localhost:8080/api/auth/register", {
        username,
        email,
        password,
      });

      toast.success("Registered successfully!");
      navigate("/login"); // Redirect to login page
    } catch (err) {
      toast.error(err.response?.data?.message || "Unable to register");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 p-5">
      <form
        onSubmit={submit}
        className="w-full max-w-sm bg-white p-8 rounded-xl shadow-md"
        aria-label="Register form"
      >
        <h1 className="text-2xl font-bold text-gray-800 mb-2 text-center">
          Fitness Tracker Lite
        </h1>
        <p className="text-sm text-gray-500 mb-6 text-center">
          Create an account to start tracking your fitness
        </p>

        <div className="flex flex-col gap-4">
          {/* Username */}
          <label className="flex flex-col text-gray-700 text-sm">
            Username
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              placeholder="Enter your username"
              className="mt-1 p-2 rounded-md border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
            />
          </label>

          {/* Email */}
          <label className="flex flex-col text-gray-700 text-sm">
            Email
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="you@domain.com"
              autoComplete="email"
              className="mt-1 p-2 rounded-md border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
            />
          </label>

          {/* Password */}
          <label className="flex flex-col text-gray-700 text-sm relative">
            Password
            <div className="relative">
              <input
                type={showPass ? "text" : "password"}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                placeholder="Enter your password"
                className="mt-1 w-full p-2 pr-10 rounded-md border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
              />
              <button
                type="button"
                onClick={() => setShowPass((s) => !s)}
                className="absolute right-2 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700 focus:outline-none"
              >
                {showPass ? "🙈" : "👁️"}
              </button>
            </div>
          </label>

          {/* Submit */}
          <button
            type="submit"
            disabled={loading}
            className={`mt-4 h-11 rounded-lg font-semibold text-white transition-colors duration-200 ${
              loading ? "bg-blue-300 cursor-not-allowed" : "bg-blue-600 hover:bg-blue-700"
            }`}
          >
            {loading ? "Registering…" : "Register"}
          </button>
        </div>

        <p className="mt-6 text-center text-gray-500 text-sm">
          Already have an account?{" "}
          <a href="/login" className="text-blue-600 hover:underline">
            Sign In
          </a>
        </p>
      </form>
    </div>
  );
}
