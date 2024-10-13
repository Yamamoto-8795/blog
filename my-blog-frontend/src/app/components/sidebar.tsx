import { useRouter } from "next/navigation";

const Sidebar = () => {
  const router = useRouter();

  return (
    <div className="sticky top-0 h-screen overflow-auto w-1/4 p-4 space-y-6 bg-gray-100 flex flex-col items-center">
      <button 
        className="w-full max-w-36 p-2 text-center text-lg font-semibold rounded hover:bg-gray-200"
        onClick={() => router.push("/articles")}
      >
        ホーム
      </button>
      <button className="w-full max-w-36 p-2 text-center text-lg font-semibold rounded hover:bg-gray-200">
        プロフィール
      </button>
      <button className="w-full max-w-36 p-2 text-center text-lg font-semibold rounded hover:bg-gray-200">
        話題検索
      </button>
      <button className="w-full max-w-36 p-2 text-center text-lg font-semibold rounded hover:bg-gray-200">
        カテゴリー検索
      </button>
      <button className="w-full max-w-36 p-2 text-center text-lg font-semibold rounded hover:bg-gray-200">
        お気に入り一覧
      </button>
      <button className="w-full max-w-36 p-2 text-center text-lg font-semibold rounded hover:bg-gray-200">
        通知
      </button>
      <button 
        className="w-full max-w-36 p-2 text-center text-lg rounded bg-blue-500 text-white font-bold hover:bg-gray-200"
        onClick={() => router.push("/articles/create")}
      >
        投稿
      </button>
    </div>
  );
};

export default Sidebar;