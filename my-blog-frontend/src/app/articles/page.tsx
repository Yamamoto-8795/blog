"use client";

import { useEffect, useState, useRef, useCallback } from 'react';
import { useRouter } from 'next/navigation';
import Sidebar from "../components/sidebar";

type Article = {
  id: number;
  title: string;
  content: string;
  likeQuantity: number;
  categoryName: string;
  createdAt: string;
  userName: string;
  userId: number;
  tags: string[];
  commentCount: number;
};

type Page<T> = {
  content: T[];
}

const ArticlePage = () => {
  const [articles, setArticles] = useState<Article[]>([]);
  const [error, setError] = useState<string>('');
  const [page, setPage] = useState<number>(0);
  const [hasMore, setHasMore] = useState<boolean>(true);
  const router = useRouter();
  const observer = useRef<IntersectionObserver | null>(null);

  const lastArticle = useCallback((node: HTMLDivElement | null) => {
    if (observer.current) {
      observer.current.disconnect();
    }

    observer.current = new IntersectionObserver(entries => {
      if (entries[0].isIntersecting && hasMore) {
        setPage(prevPage => prevPage + 1);
      }
    });

    if (node) {
      observer.current.observe(node);
    }
  }, [hasMore]);

    const fetchArticles = useCallback(async () => {
      const token = localStorage.getItem('token');

      if (!token) {
        router.push('/login');
        return;
      }

      try {
        const response = await fetch(`http://localhost:8091/api/articles?page=${page}` , {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });

        if (!response.ok) {
          throw new Error('記事を読み込めませんでした。');
        }

        const data: Page<Article> = await response.json();
        setArticles(prevArticles => {
          const newArticles = data.content.filter(article => !prevArticles.find(prevArticle => prevArticle.id === article.id));
          return [...prevArticles, ...newArticles];
        });

        setHasMore(data.content.length > 0);
      } catch (err) {
        setError('記事を読み込めませんでした。');
        console.log(err);
      }
    }, [page, router]);

    useEffect(() => {
    fetchArticles();
  }, [fetchArticles, page]);

  if (error) {
    return <div>{error}</div>
  }

  return (
    <div className="flex min-h-screen bg-white">
      <Sidebar />
      <div className="w-2/4 p-4">
        {articles.map((article, index) => (
          <div
            key={article.id}
            ref={index === articles.length - 1 ? lastArticle : null}
            className="bg-white shadow-lg rouded-lg p-6 mb-4"
          >
            <div className="flex items-center space-x-2 mb-4">
              <div className="bg-gray-200 rounded-full h-8 w-8"></div>
              <span className="font-semibold text-xl">{article.userName}</span>
              <span className="text-sm text-gray-500">{article.createdAt}</span>
            </div>
            <div className="ml-12">
              <h2 className="text-lg font-semibold mb-2">{article.title}</h2>
              <p className="text-gray-700 line-clamp-3">{article.content}</p>
              <div className="flex flex-wrap gap-2 mb-2">
                {article.tags.map(tag => (
                  <span key={tag} className="text-blue-800 text-us font-semibold mr-2 rounded">
                    {tag}
                  </span>
                ))}
              </div>
            </div>
            <div className="flex justify-around items-center text-gray-600">
              <span className="flex items-center">♡{article.likeQuantity}</span>
              <span className="flex items-center">💭{article.commentCount}</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ArticlePage;
