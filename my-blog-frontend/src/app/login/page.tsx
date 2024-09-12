"use client";

import { useForm, SubmitHandler } from 'react-hook-form';
import { useRouter } from 'next/navigation';

type FormValues = {
  mail: string
  password: string
  general?: string;
}

export default function Login() {
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<FormValues>();
  const router = useRouter();

  const onSubmit: SubmitHandler<FormValues> = async(data) => {
    try {
      const response: Response = await fetch('http://localhost:8091/api/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify (data),
      });

      if (response.status === 400) {
        const responseData = await response.json();
        if (responseData.mail || responseData.password) {
          setError("mail", { message: responseData.mail });
          setError("password", { message: responseData.password });
        } else {
          throw new Error("ログインに失敗しました。");
        }
        return;
      }

      if (!response.ok) {
        throw new Error("ログインに失敗しました。");
      }

      const responseData = await response.json();

      localStorage.setItem('token', responseData.token);
      router.push('/dashboard');
    } catch (error) {
      if (error instanceof Error) {
        setError("general", { message: error.message });
      }
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="bg-white p-8 rounded-lg shadow-mg w-full max-w-md">
        <h2 className="text-2xl font-bold text-center mb-4">ログイン</h2>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div>
            <label
              htmlFor="mail"
              className="block text-sm font-midium text-gray-700"
            >
              メールアドレス
            </label>
            <input
              type="text"
              id="mail"
              {...register('mail', { required: 'メールアドレスを入力してください。' })}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm sm:text-sm"
            />
            {errors.mail && <p className="text-red-500 mt-2">{errors.mail.message}</p>}
          </div>
          <div>
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-700"
            >
              パスワード
            </label>
            <input
              type="text"
              id="password" 
              {...register('password', { required: 'パスワードを入力してください。' })}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm sm:text-sm"
            />
            {errors.password && <p className="text-red-500 mt-2">{errors.password.message}</p>}
            <button
              type="submit"
              className="flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 mt-4"
            >
              ログイン
            </button>
          </div>
        </form>
        {errors.general && <p className="text-red-500 mt-2">{errors.general.message}</p>}
      </div>
    </div>
  )
}